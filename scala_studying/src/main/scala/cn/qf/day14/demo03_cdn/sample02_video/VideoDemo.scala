package cn.qf.day14.demo03_cdn.sample02_video

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
object VideoDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(VideoDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    //准备视频名对应的正则表达式
    val includeVideoRegex = """.*\d+\.mp4\s+.*"""
    val onlyVideoRegex = """\d+.mp4""".r
    val ipRegex =("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.)" +
      "{3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))").r

    val rdd = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/cdn/cdn.txt")
        .filter(_.matches(includeVideoRegex))
        .map(perEle => (onlyVideoRegex.findFirstIn(perEle).get,ipRegex.findFirstIn(perEle).get))
        //.foreach(println)
        .groupBy(_._1)
        .mapValues(_.toList.distinct.size)
        .cache()

    rdd.sortBy(_._2, false, 1)
      .take(10)
      .foreach(println)

    spark.stop()
  }
}
