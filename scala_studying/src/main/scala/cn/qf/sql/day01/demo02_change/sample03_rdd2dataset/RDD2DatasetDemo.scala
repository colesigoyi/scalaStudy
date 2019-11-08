package cn.qf.sql.day01.demo02_change.sample03_rdd2dataset

import org.apache.spark.sql.SparkSession

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月06日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object RDD2DatasetDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(RDD2DatasetDemo.getClass.getSimpleName)
      .getOrCreate()

    val sc = spark.sparkContext

    //方式2:RDD转DataFrame样例类方式
    val rdd =sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/sql/stu.txt")
      .map(perLine => {
        val arr = perLine.split("\\s+")
        Student(arr(0).trim.toInt,arr(1).trim,arr(2).trim.toInt)
      })
    import spark.implicits._
    println("RDD2Dataset样例类方式:")
    val ds = rdd.toDS()
    ds.show
    println("----------表结构信息--------")
    ds.printSchema()

    spark.stop()
  }
}
case class Student(id:Int, name:String, age:Int)