package cn.qf.sql.day03.sample02_hive

import cn.qf.util.SparkUtil
import org.apache.spark.sql.SaveMode

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月08日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SparkOnHiveExercise {
  def main(args: Array[String]): Unit = {
    //	SparkSession
    val spark = SparkUtil.getSparkSession(
      SparkOnHiveExercise.getClass.getName,
      "local[*]",
      isHive = true
    )
    //	初始化
    spark.sql("drop database if exists spark_on_hive_exercies cascade")
    //	建库(与此同时建立与hdfs上资源的映射关系)
    spark.sql("create database spark_on_hive_exercies")
    //	建表
    spark.sql(
      """
        |create table spark_on_hive_exercies.tb_emp(
        |name string,
        |age int,
        |isMarried boolean,
        |deptNo int
        |) row format delimited
        |fields terminated by ','
        |location 'hdfs://ns1/spark-sql/emp'
        |""".stripMargin)

    spark.sqlContext.cacheTable("spark_on_hive_exercies.tb_emp")

    //注意:下述建表时是内部表方式,但是制定了loaction,
    spark.sql(
      """
        |create table spark_on_hive_exercies.tb_external_info(
        |name string,
        |height double
        |) row format delimited
        |fields terminated by ','
        |location 'hdfs://ns1//spark-sql/emp_external_info'
        |""".stripMargin)

    spark.sqlContext.cacheTable("spark_on_hive_exercies.tb_external_info")

    spark.sql(
      """
        |select
        |* from
        |spark_on_hive_exercies.tb_emp
        |""".stripMargin).show

    spark.sql(
      """
        |select
        |* from
        |spark_on_hive_exercies.tb_external_info
        |""".stripMargin).show
    /*
    +----+---+---------+------+
    |name|age|isMarried|deptNo|
    +----+---+---------+------+
    |jack| 22|     true|     3|
    |mike| 10|    false|     2|
    | tom| 29|     true|     0|
    +----+---+---------+------+

    +----+------+
    |name|height|
    +----+------+
    |jack|167.34|
    |mike|188.45|
    | tom|198.63|
    +----+------+
     */

    //	内连接查询
    spark.sql(
      """
        |select
        |e.name `姓名`,
        |e.age `年龄`,
        |if(e.ismarried, '已婚','未婚') `婚姻状况`,
        |i.height `身高`
        |from
        |spark_on_hive_exercies.tb_emp e,
        |spark_on_hive_exercies.tb_external_info i
        |where e.name = i.name
        |""".stripMargin).show()
      /*
      +----+---+-------+------+
      |姓名|年龄|婚姻状况| 身高 |
      +----+---+-------+------+
      |jack| 22|  已婚  |167.34|
      |mike| 10|  未婚  |188.45|
      | tom| 29|  已婚  |198.63|
      +----+---+--------+------+
       */
    //	落地:
    val df = spark.sql(
      """
        |select
        |e.name,
        |e.age,
        |if(e.ismarried, '已婚','未婚') isMarried,
        |i.height
        |from
        |spark_on_hive_exercies.tb_emp e,
        |spark_on_hive_exercies.tb_external_info i
        |where e.name = i.name
        |""".stripMargin)
    //			hive表:结果是内部表
    df.write.saveAsTable("spark_on_hive_exercies.tb_final_result")
    /*
    select * from spark_on_hive_exercies.tb_final_result;
    ------+-------+--------+---------
    jack  |  22   |   已婚  |  167.34
    mike  |  10   |   未婚  |  188.45
    tom   |  29   |   已婚  |  198.63
    ------+-------+---------+--------
     */

    //			hdfs指定目录下
    df.write.mode(SaveMode.Overwrite).json("hdfs://ns1//spark-sql/output")
    /*
    {"name":"jack","age":22,"isMarried":"已婚","height":167.34}
    {"name":"mike","age":10,"isMarried":"未婚","height":188.45}
    {"name":"tom","age":29,"isMarried":"已婚","height":198.63}

     */
    //	stop
    spark.stop()
  }
}
