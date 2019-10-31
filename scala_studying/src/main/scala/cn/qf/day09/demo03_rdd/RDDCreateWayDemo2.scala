package cn.qf.day09.demo03_rdd

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月31日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object RDDCreateWayDemo2 {
  def main(args: Array[String]): Unit = {
    //准备SparkContext的实例(Spark >= 2.0 以后,推荐使用SparkSession
    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(RDDCreateWayDemo.getClass.getName)
      .getOrCreate()

    val sc: SparkContext = spark.sparkContext

    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/log/input/hello.txt")
      .foreach(println)
    spark.stop()
  }
}
