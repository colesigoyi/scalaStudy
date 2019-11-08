package cn.qf.sql.day02.demo01_change.sample02_dataset2dataframe

import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Dataset2DataFrameDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(Dataset2DataFrameDemo.getClass.getSimpleName)
      .getOrCreate()
    val ds:Dataset[String] =spark.read.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/sql/stu.txt")
    val df = ds.toDF()
    df.show()
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
    spark.stop()
  }
}
