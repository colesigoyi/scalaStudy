package cn.qf.day08.demo01_wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月30日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WordCountApplication{
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println(
        """
          |警告！传入的参数有误！
          |请输入参数: <inputPath>  <outputPath>
          |""".stripMargin)
      sys.exit(-1)
    }
    val inputPath = args(0).trim
    val outputPath = args(1).trim

    val conf = new SparkConf().setMaster("local[*]")
                .setAppName(WordCountApplication
                .getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val reduceRDD: RDD[(String, Int)] = sc.textFile(inputPath)
      .flatMap(_.split("\\s+"))
      .filter(_.trim != "")
      .map((_,1))
      .reduceByKey(_ + _)
        .sortBy(_._1,false, 1)
    //reduceRDD.saveAsTextFile(outputPath)
    reduceRDD.foreach(println)
    sc.stop
  }
}
