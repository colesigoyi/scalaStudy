package cn.qf.sql.day01.demo02_change.sample02_dataframe2rdd

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
object DataFrame2RDDDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName(DataFrame2RDDDemo.getClass.getSimpleName)
      .getOrCreate()
    //直接读取磁盘上json格式的数据,装载为DataFrame
    val df = spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/log/jsonoutput")
      //.show()
      /*
      +---+---------+------+-------+------+
      |age|faceValue|height|   name|weight|
      +---+---------+------+-------+------+
      | 18|     80.9| 174.6|  marry|  50.3|
      | 20|     98.9| 179.1|lucycat|  48.1|
      | 21|     80.9| 172.1|  Angle|  56.8|
      | 21|     80.9| 172.1|   kate|  56.1|
      | 21|     80.9| 176.2| lilydo|  51.5|
      +---+---------+------+-------+------+
       */

    //转换成RDD
    val rdd = df.rdd
    rdd.map(perRow => {
      val age = perRow.getAs[Long]("age")
      val faceValue = perRow.getAs[Double]("faceValue")
      val height = perRow.getAs[Double]("height")
      val name = perRow.getAs[String]("name")
      val weight = perRow.getAs[Double]("weight")
      (name, age, faceValue, height, weight + 3)
    }).foreach(println)

    spark.stop()
  }
}
