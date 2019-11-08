package cn.qf.sql.day02.demo03_lac

import org.apache.spark.sql.SparkSession

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object LacDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(LacDemo.getClass.getSimpleName)
      .getOrCreate()

    val sc = spark.sparkContext

    //导入
    import spark.implicits._
    //	1:读取日志信息文件,装载到内存中的RDD,转换成DataFrame,映射成虚拟表tb_log
    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/" +
      "loc/mobilelocation/log")
        .map(perLine => {
          val arr = perLine.split(",")
          val phone = arr(0).trim
          var time = arr(1).trim.toLong
          val lacID = arr(2).trim
          val flg = arr(3).trim.toInt
          if (flg == 1) {
            time = -time
          }
          (phone,time,lacID)
        })
        .toDF("phone","time","lacID")
        //.show()
        .createOrReplaceTempView("tb_log")

    //优化:缓存->将经常使用的表缓存起来
    spark.sqlContext.cacheTable("tb_log")


    //	2:针对tb_log进行分析,求出不同用户停留时长最长的两个基站
//    spark.sql(
//      """
//        |select *
//        |from (
//          |select
//          |t.phone,
//          |t.time,
//          |t.lacID,
//          |rank() over(partition by phone sort by time desc) rank
//          |from (
//            |select
//            |phone,
//            |sum(time) time,
//            |lacID
//            |from tb_log
//            |group by phone,lacID
//          |) t
//        |) tt
//        |where rank <=2
//        |""".stripMargin)
//      .createTempView("log_top2")


//    spark.sql("select * from log_top2")
//        .show

    /*
    +-----------+-----+--------------------+
    |      phone| time|               lacID|
    +-----------+-----+--------------------+
    |18101056888|97500|16030401EAFB68F1E...|
    |18101056888|54000|9F36407EAD8829FC1...|
    |18688888888|87600|16030401EAFB68F1E...|
    |18688888888|51200|9F36407EAD8829FC1...|
    +-----------+-----+--------------------+
     */
    //	3:读取基站文件,装载到内存中的RDD转换成DataFrame,映射成虚拟表tb_lac

    //9F36407EAD8829FC166F14DDE7970F68,116.304864,40.050645,6

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/loc/mobilelocation/lac_info.txt")
      .map(perLine => {
        val arr = perLine.split(",")
        val lacID = arr(0).trim
        var W = arr(1).trim
        val E = arr(2).trim
        (lacID, W, E)
      })
      .toDF("lacID","W","E")
      //.show()
      .createOrReplaceTempView("tb_lac")

    //优化:缓存->将经常使用的表缓存起来
    spark.sqlContext.cacheTable("tb_lac")

    //	4:上述两张表内连接查询,得出最终的结果
//    spark.sql(
//      """
//        |select
//        |*
//        |from
//        |tb_lac
//        |""".stripMargin).show
    /*
    +--------------------+----------+---------+
    |               lacID|         W|        E|
    +--------------------+----------+---------+
    |9F36407EAD8829FC1...|116.304864|40.050645|
    |CC0710CC94ECC657A...|116.303955|40.041935|
    |16030401EAFB68F1E...|116.296302|40.032296|
    +--------------------+----------+---------+
     */
    spark.sql(
      """
        |select
        | log.phone `手机号码`,
        | lac.lacID `基站ID`,
        | lac.W `经度`,
        | lac.E `纬度`,
        | log.time `时长`
        |from
        |(
            |select *
            |from (
                |select
                |t.phone,
                |t.time,
                |t.lacID,
                |rank() over(partition by phone sort by time desc) rank
                |from (
                    |select
                    |phone,
                    |sum(time) time,
                    |lacID
                    |from tb_log
                    |group by phone,lacID
                |) t
            |) tt
            |where rank <=2
        |) log,
        |tb_lac lac
        |where log.lacID = lac.lacID
        |""".stripMargin).show
      /*
  +-----------+--------------------+----------+---------+-----+
  |  手机号码  |      基站ID        |    经度   |   纬度   | 时长|
  +-----------+--------------------+----------+---------+-----+
  |18101056888|9F36407EAD8829FC1...|116.304864|40.050645|54000|
  |18688888888|9F36407EAD8829FC1...|116.304864|40.050645|51200|
  |18101056888|16030401EAFB68F1E...|116.296302|40.032296|97500|
  |18688888888|16030401EAFB68F1E...|116.296302|40.032296|87600|
  +-----------+--------------------+----------+---------+-----+
       */

    spark.stop()
  }
}
//手机号码: 18101056888,经纬度: (116.304864,40.050645),停留时长: 54000
//手机号码: 18101056888,经纬度: (116.296302,40.032296),停留时长: 97500
//手机号码: 18688888888,经纬度: (116.304864,40.050645),停留时长: 51200
//手机号码: 18688888888,经纬度: (116.296302,40.032296),停留时长: 87600