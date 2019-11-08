package cn.qf.sql.day02.demo02_codingway.sample01_dsl

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
object DSLDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(DSLDemo.getClass.getSimpleName)
      .setMaster("local")

    val spark = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()

    val df = spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/log/jsonoutput")
    //使用DSL风格的语法,求出学生的姓名和年龄
    import spark.implicits._

    println("------------------------------")
    df.filter($"age">18)
      .select($"name",$"age")
      .show
    /*
    +-------+---+
    |   name|age|
    +-------+---+
    |lucycat| 20|
    |  Angle| 21|
    |   kate| 21|
    | lilydo| 21|
    +-------+---+
     */
    println("------------------------------")
    //求出不同年龄段学生总人数
    df.groupBy($"age")
      .count()
      .show()
    /*
    +---+-----+
    |age|count|
    +---+-----+
    | 18|    1|
    | 21|    3|
    | 20|    1|
    +---+-----+
     */
  }
}
