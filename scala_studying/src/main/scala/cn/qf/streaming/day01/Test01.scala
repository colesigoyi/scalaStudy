package cn.qf.streaming.day01

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月11日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Test01 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName(this.getClass.getSimpleName)

    val sc = new SparkContext(conf)

    val rdd1 = sc.parallelize(List("hello java", "hello java", "good student"))
    val tup = rdd1.flatMap(_.split("\\s+"))
        .map((_, 1))
    val rdd3 = sc.parallelize(List(("hello",1)))
    val result:RDD[(String, Int)] = tup.subtractByKey(rdd3)

    println(s"${result.collect.toBuffer}")
    //ArrayBuffer((java,1), (java,1), (student,1), (good,1))
    println(rdd3.partitions.length)
    val coalesce = rdd3.coalesce(4, shuffle = true)

    println(coalesce.partitions.length)
    println(coalesce.coalesce(1).partitions.length)
    //多分区变少分区,不用发生shuffle

    tup.partitionBy(new HashPartitioner(3))
    tup.countApproxDistinct()


    sc.stop()
  }
}
