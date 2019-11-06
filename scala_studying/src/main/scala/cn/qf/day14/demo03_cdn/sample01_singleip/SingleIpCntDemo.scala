package cn.qf.day14.demo03_cdn.sample01_singleip

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月06日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SingleIpCntDemo extends App{
  val spark = SparkUtil.getSparkSession(SingleIpCntDemo.getClass.getName,"local[*]")
  val sc = spark.sparkContext

  //准备正则表达式
//  val ipRegex = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))".r
  val ipRegex ="((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))".r
  val rdd = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
    "09_scala/scala_studying/cdn/cdn.txt")
      .map(perLine => (ipRegex.findFirstIn(perLine).get, 1))
      .reduceByKey(_ + _)
      .cache()

  //求出不同ip地址的总数
  val cnt = rdd.count()

  //求出最受欢迎的前十个ip地址
  val top10 = rdd.sortBy(_._2, ascending = false, 1)
      .take(10)
      .toBuffer

  println(s"不同ip地址的总数是:$cnt")
  println(s"最受欢迎的top10的ip地址是:$top10")

  spark.stop()
}
