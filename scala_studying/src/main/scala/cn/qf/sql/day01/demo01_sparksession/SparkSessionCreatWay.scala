package cn.qf.sql.day01.demo01_sparksession

import org.apache.spark.SparkConf
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
object SparkSessionCreatWay {
  def main(args: Array[String]): Unit = {
    //SparkSession实例的三种创建方式
    //方式1:Builder创建方式
    println("------------------Builder-------------------")
    val spark = SparkSession.builder()
      .master("local")
      .appName(SparkSessionCreatWay.getClass.getSimpleName)
      .getOrCreate()
    println(s"Builder构建器方式创建SparkSession实例是:$spark")
    spark.stop()

    println("------------------SparkConf-------------------")
    val conf = new SparkConf()
    conf.setAppName(SparkSessionCreatWay.getClass.getSimpleName)
      .setMaster("local")
    val spark2 = SparkSession.builder()
      .config(conf)
      .getOrCreate()
    println(s"SparkConf构建器方式创建SparkSession实例是:$spark2")
    spark2.stop()

    println("-----------------enableHiveSupport--------------------")

    val spark3 = SparkSession.builder()
      .master("local")
      .appName(SparkSessionCreatWay.getClass.getSimpleName)
      .enableHiveSupport()
      .getOrCreate()
    println(s"enableHiveSupport构建器方式创建SparkSession实例是:$spark3")
    spark3.stop()
  }
}
