package cn.qf.sql.day01.demo02_change.sample01_rdd2dataframe

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
object RDD2DateFrameDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName(RDD2DateFrameDemo.getClass.getSimpleName)
      .getOrCreate()

    val sc = spark.sparkContext

    //方式1:RDD转DataFrame元组方式
    val rdd =sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/sql/stu.txt")
        .map(perLine => {
          val arr = perLine.split("\\s+")
          (arr(0).trim.toInt,arr(1).trim,arr(2).trim.toInt)
        })
    import spark.implicits._

    rdd.toDF("id","name","age").show

    spark.stop()
  }
}
