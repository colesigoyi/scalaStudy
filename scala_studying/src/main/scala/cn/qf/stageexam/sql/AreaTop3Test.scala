package cn.qf.stageexam.sql

import java.util
import java.util.Properties

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataTypes, StructField}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.json4s.jackson.Json

/**
  * 需求：按区域统计top3热门商品
  * 实现思路：
  *   1. 创建user_visit_action、product_info表到当前SparkSession并加载数据
  *   2. 从表user_visit_action中获取基础数据：用户点击流日志
  *     获取的字段有：city_id、click_product_id
  *     获取条件：日期范围为：startDate="2019-08-15"，endDate="2019-08-15"
  *              click_product_id（用户点击商品id字段）限定为不为空
  *   3. 获取mysql的city_info表的城市信息
  *   4. 将点击流日志和城市信息进行join，生成临时表tmp_click_product_basic，字段有：cityId, cityName, area, click_product_id
  *   5. 根据表tmp_click_product_basic，统计各区域商品点击次数并生成临时表tmp_area_product_click_count，字段有：area,product_id,click_count,city_infos
  *     city_infos的统计要求：
  *       因为每个区域有很多城市，需要将各个区域涉及到的城市信息拼接起来，比如华南区有广州和三亚，拼接后的city_infos为："4:三亚,3:广州"，其中数字4和3为city_id
  *       此时需要用到GroupConcatDistinctUDAF函数
  *   6. 将各区域商品点击次数临时表tmp_area_product_click_count的product_id字段去关联商品信息表(product_info)的product_id
  *     product_info表的extend_info字段值为json串，需要特殊处理："0"和"1"分别代表了自营和第三方商品
  *     需要用GetJsonObjectUDF函数是从json串中获取指定字段的值，如果product_status为0，返回值为"自营"，如果为1，返回值为"第三方"
  *     生成临时表tmp_area_fullprod_click_count的字段有：
  *     area,product_id,click_count,city_infos,product_name,product_status
  *   7. 将tmp_area_fullprod_click_count进行统计每个区域的top3热门商品（使用开窗函数进行子查询）
  *      统计过程中的外层查询需要增加area_level字段，即按照区域进行分级：
  *      区域有：华北、华东、华南、华中、西北、西南、东北
  *         A级：华北、华东
  *         B级：华南、华中
  *         C级：西北、西南
  *         D级：东北
  *      得到的结果字段有：area、area_level、product_id、city_infos、click_count、product_name、product_status
  *   8. 将结果保存到mysql的area_top3_product表中
  *
  */
/**
 * 2019-08-1522ffad82388eb449bab8acad73ed4f009282019-08-15 12:40:50null6143nullnullnullnull2
 * 2019-08-1522ffad82388eb449bab8acad73ed4f009282019-08-15 12:27:18null61nullnullnull90382
 * 2019-08-152225a8c16b088642de9876a0c7efb7419972019-08-15 10:42:08火锅nullnullnullnullnullnull9
 * 2019-08-152225a8c16b088642de9876a0c7efb7419972019-08-15 10:00:12nullnullnull7222nullnull0
 * 2019-08-152225a8c16b088642de9876a0c7efb7419972019-08-15 10:58:33null861nullnullnullnull5
 */
/**
 * 13product13{"product_status":1}
 * 14product14{"product_status":0}
 * 15product15{"product_status":0}
 * 16product16{"product_status":0}
 * 17product17{"product_status":0}
 */
/*
// 创建表user_visit_action并加载数据
spark.sql("CREATE TABLE IF NOT EXISTS user_visit_action (session_date string, user_id int, session_id string, page_id int, action_time string, search_keyword string, click_category_id int, click_product_id int, order_category_ids string, order_product_ids string, pay_category_ids string, pay_product_ids string, city_id int)")
spark.sql("LOAD DATA LOCAL INPATH  'dir/user_visit_action.txt' INTO TABLE user_visit_action")
 */
object AreaTop3Test {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName(this.getClass.getName)
      .master("local[2]")
      .getOrCreate()

    // 指定获取数据的开始时间和结束时间
    val startDate = "2019-08-15"
    val endDate = "2019-08-15"

    val sc = spark.sparkContext

    import spark.implicits._

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/dir/user_visit_action.txt")
      .map(perLine => {
        val arr = perLine.split("\\s+")
        val session_date = arr(0).trim
        val user_id = arr(1).trim
        val session_id = arr(2).trim
        val page_id = arr(3).trim
        val action_time = arr(4).trim
        val search_keyword = arr(6).trim
        val click_category_id = arr(7).trim
        val click_product_id = arr(8).trim
        val order_category_ids = arr(9).trim
        val order_product_ids = arr(10).trim
        val pay_category_ids = arr(11).trim
        val pay_product_ids = arr(12).trim
        val city_id = arr(13).trim

        (session_date,user_id,session_id,page_id,action_time,search_keyword,click_category_id,
          click_product_id,order_category_ids,order_product_ids,pay_category_ids,pay_product_ids,city_id)
      })
      //.foreach(println)
    /*
    (2019-08-15,82,b544b160dfe8430f8a7595900ca4bc19,8,2019-08-15,19:56:58,重庆辣子鸡,null,null,null,null,null,null)
(2019-08-15,82,b544b160dfe8430f8a7595900ca4bc19,0,2019-08-15,19:40:39,null,null,null,null,null,96,77)
(2019-08-15,82,b544b160dfe8430f8a7595900ca4bc19,2,2019-08-15,19:02:36,null,null,null,83,59,null,null)
(2019-08-15,82,b544b160dfe8430f8a7595900ca4bc19,5,2019-08-15,19:20:50,null,null,null,31,32,null,null)
(2019-08-15,82,b544b160dfe8430f8a7595900ca4bc19,9,2019-08-15,19:55:52,null,null,null,26,41,null,null)
(2019-08-15,82,b544b160dfe8430f8a7595900ca4bc19,3,2019-08-15,19:30:31,null,56,36,null,null,null,null)
(2019-08-15,82,b544b160dfe8430f8a7595900ca4bc19,8,2019-08-15,19:25:51,null,56,null,null,null,40,72)
     */
      .toDF("session_date","user_id","session_id","page_id","action_time","search_keyword","click_category_id",
        "click_product_id","order_category_ids","order_product_ids","pay_category_ids","pay_product_ids","city_id")
      .createOrReplaceTempView("user_visit_action")
    spark.sqlContext.cacheTable("user_visit_action")

    spark.sql(
      """
        |select
        |city_id,
        |click_product_id
        |from
        |user_visit_action
        |where action_time >= "2019-08-15" and action_time <= "2019-08-15" and click_product_id != "null"
        |""".stripMargin)
        .createOrReplaceTempView("city_click_product_id")
      spark.sqlContext.cacheTable("city_click_product_id")
      //.show()
        /*
       +-------+----------------+
      |city_id|click_product_id|
      +-------+----------------+
      |      5|              41|
      |      5|              76|
      |      3|              21|
      |      3|              84|
      |      0|              77|
      |      2|              84|
      |      5|              39|
      |      8|               6|
      |      9|              12|
      +-------+----------------+
         */

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/dir/product_info.txt")
      .map(perLine => {
        val arr = perLine.split("\\s+")
        val product_id = arr(0).trim
        val product_name = arr(1).trim
        val str = arr(2).trim
        val extend_info = str.substring(str.length-2, str.length-1)

        (product_id,product_name,extend_info)
      })
      .toDF("product_id","product_name","extend_info")
      .createOrReplaceTempView("tmp_product_info")
    spark.sqlContext.cacheTable("tmp_product_info")


    spark.sql(
      """
        |select
        |product_id,
        |product_name,
        |extend_info,
        |if(extend_info=1,"第三方","自营") product_status
        |from
        |tmp_product_info
        |""".stripMargin)
      //.show()
      .createOrReplaceTempView("product_info")
    spark.sqlContext.cacheTable("product_info")

    /*
+----------+------------+-----------+--------------+
|product_id|product_name|extend_info|product_status|
+----------+------------+-----------+--------------+
|         0|    product0|          1|           第三方|
|         1|    product1|          1|           第三方|
|         2|    product2|          0|            自营|
|         3|    product3|          1|           第三方|
|         4|    product4|          1|           第三方|
|         5|    product5|          0|            自营|
|         6|    product6|          1|           第三方|
|         7|    product7|          1|           第三方|
|         8|    product8|          1|           第三方|
|         9|    product9|          1|           第三方|
|        10|   product10|          1|           第三方|
|        11|   product11|          1|           第三方|
|        12|   product12|          0|            自营|
|        13|   product13|          1|           第三方|
|        14|   product14|          0|            自营|
|        15|   product15|          0|            自营|
|        16|   product16|          0|            自营|
|        17|   product17|          0|            自营|
|        18|   product18|          1|           第三方|
|        19|   product19|          0|            自营|
+----------+------------+-----------+--------------+
 */

    val properties: Properties = new Properties()
        properties.put("driver", "com.mysql.jdbc.Driver")
        properties.put("user", "root")
        properties.put("password", "root")
        //读取db之study中的表
    spark.read.jdbc(getProperties()._2, "city_info", getProperties()._1)
          .createOrReplaceTempView("city_info")
    spark.sqlContext.cacheTable("city_info")

    spark.sql(
      """
        |select
        |*
        |from
        |city_info
        |""".stripMargin)
    //.show()
    /*
    +-------+---------+----+
    |city_id|city_name|area|
    +-------+---------+----+
    |      0|       北京|  华北|
    |      1|       上海|  华东|
    |      2|       南京|  华东|
    |      3|       广州|  华南|
    |      4|       三亚|  华南|
    |      5|       武汉|  华中|
    |      6|       长沙|  华中|
    |      7|       西安|  西北|
    |      8|       成都|  西南|
    |      9|      哈尔滨|  东北|
      ...
    +-------+---------+----+
     */
    spark.sql(
      """
        |select
        |ci.city_id cityId,
        |ci.city_name cityName,
        |ci.area,
        |ccpi.click_product_id
        |from
        |city_click_product_id ccpi
        |join
        |city_info ci
        |where ci.city_id = ccpi.city_id
        |""".stripMargin)
      //.show()
      .createOrReplaceTempView("tmp_click_product_basic")
    spark.sqlContext.cacheTable("tmp_click_product_basic")
    /*
    +-------+---------+----+----------------+
    |city_id|city_name|area|click_product_id|
    +-------+---------+----+----------------+
    |      1|       上海|  华东|              38|
    |      1|       上海|  华东|              72|
    |      1|       上海|  华东|               4|
    |      1|       上海|  华东|              21|
    |      1|       上海|  华东|              47|
    |      1|       上海|  华东|              35|
      ...
    +-------+---------+----+----------------+

     */

    val groupConcatDistinctUDAF = spark.udf.register("groupConcatDistinctUDAF",new GroupConcatDistinctUDAF)

    spark.sql(
      """
        |select
        |area,
        |click_product_id product_id,
        |sum(click_count) click_count,
        |groupConcatDistinctUDAF(city_infos) city_infos
        |from
        |(
            |select
            |area,
            |click_product_id,
            |count(*) click_count,
            |concat(cityId,":",cityName) city_infos
            |from
            |tmp_click_product_basic
            |group by
            |area,click_product_id,city_infos
        |)
        |group by
        |area,product_id
        |""".stripMargin)
      //.show()
      .createOrReplaceTempView("tmp_area_product_click_count")
    spark.sqlContext.cacheTable("tmp_area_product_click_count")
/*
+----+----------------+-----------+----------+
|area|click_product_id|click_count|city_infos|
+----+----------------+-----------+----------+
|  东北|              19|          3|     9:哈尔滨|
|  华东|              14|          4| 1:上海,2:南京|
|  华中|              11|          5| 6:长沙,5:武汉|
|  华北|              13|          3|      0:北京|
|  华北|              44|          2|      0:北京|
|  华南|              27|          6| 3:广州,4:三亚|
|  西南|              53|          2|      8:成都|
|  西北|              36|          7|      7:西安|
|  华东|              27|          2| 1:上海,2:南京|
|  东北|              85|          3|     9:哈尔滨|
|  华中|              22|          7|      6:长沙|
|  华南|              28|          6| 3:广州,4:三亚|
|  西北|              60|          2|      7:西安|
|  西南|              37|          4|      8:成都|
|  西南|              58|          4|      8:成都|
|  华东|              40|          3| 2:南京,1:上海|
|  西南|              28|          5|      8:成都|
|  华东|              28|         10| 2:南京,1:上海|
|  华东|              83|          4| 1:上海,2:南京|
|  华南|              18|          5| 4:三亚,3:广州|
+----+----------------+-----------+----------+
 */
  spark.sql(
    """
      |select
      |tapcc.area,
      |tapcc.product_id,
      |tapcc.click_count,
      |tapcc.city_infos,
      |pi.product_name,
      |pi.product_status
      |from
      |tmp_area_product_click_count tapcc
      |join
      |product_info pi
      |on
      |tapcc.product_id = pi.product_id
      |""".stripMargin)
    //.show()
    .createOrReplaceTempView("tmp_area_fullprod_click_count")
    spark.sqlContext.cacheTable("tmp_area_fullprod_click_count")
    /*
    +----+----------+-----------+----------+------------+--------------+
    |area|product_id|click_count|city_infos|product_name|product_status|
    +----+----------+-----------+----------+------------+--------------+
    |  华北|        51|          4|      0:北京|   product51|           第三方|
    |  西北|        51|          2|      7:西安|   product51|           第三方|
    |  东北|        51|          4|     9:哈尔滨|   product51|           第三方|
    |  华中|        51|          7| 6:长沙,5:武汉|   product51|           第三方|
    |  西南|        51|          2|      8:成都|   product51|           第三方|
    |  华东|        51|          5| 1:上海,2:南京|   product51|           第三方|
    |  华南|        51|          9| 3:广州,4:三亚|   product51|           第三方|
    |  华中|         7|          5| 6:长沙,5:武汉|    product7|           第三方|
    |  东北|         7|          1|     9:哈尔滨|    product7|           第三方|
    |  西南|         7|          1|      8:成都|    product7|           第三方|
    |  华南|         7|          5| 3:广州,4:三亚|    product7|           第三方|
    |  华北|         7|          4|      0:北京|    product7|           第三方|
    |  西北|         7|          3|      7:西安|    product7|           第三方|
    |  华南|        15|          7| 4:三亚,3:广州|   product15|            自营|
    |  华东|        15|          2|      1:上海|   product15|            自营|
    |  华北|        15|          2|      0:北京|   product15|            自营|
    |  东北|        15|          1|     9:哈尔滨|   product15|            自营|
    |  西南|        15|          3|      8:成都|   product15|            自营|
    |  华中|        15|          6| 6:长沙,5:武汉|   product15|            自营|
    |  西北|        15|          5|      7:西安|   product15|            自营|
    +----+----------+-----------+----------+------------+--------------+
     */
  spark.sql(
    """
       |select
       |sc.area,
       |(case when area = "华东" then "A级"
       |when area = '华北' then 'A级'
       |when area = '华南' then 'B级'
       |when area = '华中' then 'B级'
       |when area = '西北' then 'C级'
       |when area = '西南' THEN 'C级'
       |when area = '东北' THEN 'D级'
       |else 'null'
       |end) as area_level,
       |sc.product_id,
       |sc.city_infos,
       |sc.click_count,
       |sc.product_name,
       |sc.product_status
       |from
       |(
         |select
         |s.area,
         |s.product_id,
         |s.click_count,
         |s.city_infos,
         |s.product_name,
         |s.product_status,
         |row_number() over(partition by s.area order by s.click_count desc) rank
         |from (
           |select
           |*
           |from
           |tmp_area_fullprod_click_count
       |  ) s
       |) sc
       |where rank <= 3
      |""".stripMargin)
    //.show()
    /*
+----+----------+----------+-----------+----------+------------+--------------+
|area|area_level|product_id|click_count|city_infos|product_name|product_status|
+----+----------+----------+-----------+----------+------------+--------------+
|  华东|        A级|        87|         11| 1:上海,2:南京|   product87|           第三方|
|  华东|        A级|        28|         10| 2:南京,1:上海|   product28|           第三方|
|  华东|        A级|        98|         10| 1:上海,2:南京|   product98|            自营|
|  西北|        C级|        97|          8|      7:西安|   product97|           第三方|
|  西北|        C级|        13|          8|      7:西安|   product13|           第三方|
|  西北|        C级|        36|          7|      7:西安|   product36|           第三方|
|  华南|        B级|        82|         13| 3:广州,4:三亚|   product82|           第三方|
|  华南|        B级|        87|         11| 4:三亚,3:广州|   product87|           第三方|
|  华南|        B级|        70|         11| 4:三亚,3:广州|   product70|            自营|
|  华北|        A级|        30|          7|      0:北京|   product30|            自营|
|  华北|        A级|        25|          7|      0:北京|   product25|           第三方|
|  华北|        A级|         3|          6|      0:北京|    product3|           第三方|
|  东北|        D级|        59|          6|     9:哈尔滨|   product59|            自营|
|  东北|        D级|        17|          6|     9:哈尔滨|   product17|            自营|
|  东北|        D级|        82|          6|     9:哈尔滨|   product82|           第三方|
|  华中|        B级|        76|         12| 5:武汉,6:长沙|   product76|            自营|
|  华中|        B级|        39|         11| 5:武汉,6:长沙|   product39|           第三方|
|  华中|        B级|        59|         10| 6:长沙,5:武汉|   product59|            自营|
|  西南|        C级|         8|          7|      8:成都|    product8|           第三方|
|  西南|        C级|        36|          7|      8:成都|   product36|           第三方|
+----+----------+----------+-----------+----------+------------+--------------+
     */
    .write
    .mode(SaveMode.Append)
    .jdbc(getProperties()._2, "area_top3_product", getProperties()._1)
    spark.stop()
  }

  /**
    * 请求数据库的配置信息
    *
    * @return
    */
  def getProperties()= {
    val prop = new Properties()
    prop.put("driver", "com.mysql.jdbc.Driver")
    prop.put("user", "root")
    prop.put("password", "root")
    val url: String = "jdbc:mysql://localhost:3306/test1122?useUnicode=true&characterEncoding=utf-8"
    (prop, url)
  }
  def getNum(str: String): String = {
    val str1: String = str.substring(str.length-2, str.length-1)
    str1
  }
}
