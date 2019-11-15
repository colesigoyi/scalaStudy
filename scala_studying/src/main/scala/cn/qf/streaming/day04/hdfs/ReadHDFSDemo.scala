package cn.qf.streaming.day04.hdfs

import cn.qf.util.SparkUtil
import org.apache.spark.SparkContext
import org.apache.spark.sql.catalyst.expressions.Second
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月14日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ReadHDFSDemo {
  def main(args: Array[String]): Unit = {
    val conf = SparkUtil.getSparkConf
    val sc = new StreamingContext(conf, Seconds(5))
    val words = sc.textFileStream("hdfs://ns1/spark-streaming/myself/")
    words.flatMap(_.split("\\s+"))
      .map((_, 1))
      .reduceByKey(_ + _)

    words.print()
    // hadoop fs -put teststreaming.txt /spark-streaming/myself/20191114_1.txt
    /*
    hello hello good
    friend boy girl
    hello
    world
    world
    hehe hehe hehe
     */

    sc.start()
    sc.awaitTermination()
  }
}
