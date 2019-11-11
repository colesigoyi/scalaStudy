package cn.qf.core.day09.demo03_rdd

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
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
object RDDCreateWayDemo {
  def main(args: Array[String]): Unit = {
    //准备SparkContext的实例(Spark >= 2.0 以后,推荐使用SparkSession
    val spark:SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(RDDCreateWayDemo.getClass.getName)
      .getOrCreate()

    val sc:SparkContext = spark.sparkContext
    //makeRDD
    val rdd:RDD[Int] = sc.makeRDD(Seq(1,2,3,4))
    rdd.foreach(println)
    println("------------------------------")

    //parreliliize
    val rdd2:RDD[Int] = sc.parallelize(List(1,2,3,4), 4)
    rdd2.foreach(println)
    //资源释放
    spark.stop()
  }
}
