package cn.qf.sql.day02.demo01_change.sample01_dataset2rdd

import org.apache.spark.sql.{Dataset, Row, SparkSession}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Dataset2RDDDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(Dataset2RDDDemo.getClass.getSimpleName)
      .getOrCreate()

    val ds =spark.read.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/sql/stu.txt")
    val dd:Dataset[Row] = spark.read.json("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/log/jsonoutput/part-00000")
    dd.show()
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
    ds.printSchema()
    /*
    root
    |-- value: string (nullable = true)
     */

    println("------------")
    //show方式默认显示Dataset数据集的前20行
    ds.show()
    /*
    +------------+
    |       value|
    +------------+
    |   1 jack 12|
    |2 lucycat 18|
    |  3 angle 19|
    |  4 marry 28|
    |   5 lily 16|
    +------------+
     */

    println("------------")
    //Dataset->RDD
    val rdd = ds.rdd
    rdd.foreach(println)
    /*
      1 jack 12
      2 lucycat 18
      3 angle 19
      4 marry 28
      5 lily 16
     */

    spark.stop()
  }
}
