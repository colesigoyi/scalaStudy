package cn.qf.core.day13.demo03_checkpoint

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CheckPointDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CheckPointDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //步骤
    //1.开启CheckPoint
    sc.setCheckpointDir("hdfs://192.168.8.101:9000/ck")
    //2.rdd进行一些列计算
    val rdd = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/" +
      "scala_studying/ad/ad.txt", 1)
        .flatMap(_.split("\\s+"))
        .map((_, 1))
        .reduceByKey(_ + _)
    //3.将全局聚合之后的rdd进行CheckPoint
    rdd.checkpoint()
    //4.调用action算子进行触发
    rdd.foreach(println)

    //显示CheckPoint保存的目标文件所在目录
    println(s"保存的目标目录是:${sc.getCheckpointDir}")

    spark.stop()
  }
}
