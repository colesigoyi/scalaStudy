package cn.qf.sql.day02.demo02_codingway.sample02_sql

import org.apache.spark.SparkConf
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
object SQLDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(SQLDemo.getClass.getSimpleName)
      .setMaster("local")

    val spark = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()

    val df = spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/log/jsonoutput")

    df.createOrReplaceTempView("beautifulGirl")

    //需求1:求出所有学生信息,显示学生名字和年龄
    val age = 18
    spark.sql(
      s"""
        |select
        |name `姓名`,
        |age `年龄`
        |from beautifulGirl
        |where age >= $age
        |""".stripMargin)
      .show
    /*
    +-------+---+
    | 姓名 | 年龄|
    +-------+---+
    |  marry| 18|
    |lucycat| 20|
    |  Angle| 21|
    |   kate| 21|
    | lilydo| 21|
    +-------+---+
     */
    //需求2:年龄段总人数
    spark.sql(
      """
        |select
        |age `年龄`,
        |count(age) `总人数`
        |from beautifulGirl
        |group by age
        |""".stripMargin)
      .show
    /*
    +---+---+
    | 年龄|总人数|
    +---+---+
    | 18|  1|
    | 21|  3|
    | 20|  1|
    +---+---+
     */
    spark.stop()
  }
}
