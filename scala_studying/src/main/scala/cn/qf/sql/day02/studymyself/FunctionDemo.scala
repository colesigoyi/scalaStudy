package cn.qf.sql.day02.studymyself

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
object FunctionDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Demo01")
      .setMaster("local")

    val spark = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()

    val df = spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/log/jsonoutput")
    df.createOrReplaceTempView("beautifulGirl")
    df.show()
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
    spark.sql("select * from beautifulGirl order by faceValue desc limit 1").show()
    /*
    +---+---------+------+-------+------+
    |age|faceValue|height|   name|weight|
    +---+---------+------+-------+------+
    | 20|     98.9| 179.1|lucycat|  48.1|
    +---+---------+------+-------+------+
     */
  }
}
