package cn.qf.sql.day01.demo02_change.sample01_rdd2dataframe


import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月06日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object RDD2DateFrameDemo3 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local")
      .appName(RDD2DateFrameDemo3.getClass.getSimpleName)
      .getOrCreate()

    val sc = spark.sparkContext

    //方式3:RDD转DataFrame的Structtype方式
    val rdd:RDD[Row] = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/sql/stu.txt")
      .map(perLine => {
        val arr = perLine.split("\\s+")
        Row(arr(0).trim.toInt, arr(1).trim, arr(2).trim.toInt)
      })

    val structType:StructType = StructType(Seq(
      StructField("id",IntegerType,nullable = false),
      StructField("name",StringType,nullable = false),
      StructField("id",IntegerType,nullable = false)
    ))

    spark.createDataFrame(rdd,structType)
        .show

    spark.stop()
  }
}
