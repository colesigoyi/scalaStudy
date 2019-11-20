package cn.qf.testbydemo

import cn.qf.util.SparkUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.{Accumulable, Accumulator, SparkConf, SparkContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月18日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object AccumulatorTestByDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = SparkUtil.getSparkConf()
    val sc = new SparkContext(conf)
    val file: RDD[String] = sc.textFile("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/ad/blankdemo.txt")
    val blankLines: Accumulator[Int] = sc.accumulator(0)
    file.flatMap(_.split("\\s+"))
      .map((perLine: String) => {
      if (perLine == "\t") {
        blankLines += 1
      }
    })
    println("Blank Lines : " + blankLines.value)
  }
}
