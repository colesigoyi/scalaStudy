package cn.qf.sql.day02.demo01_change.sample03_dataframe2dataset

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object DataFrame2Dataset {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(DataFrame2Dataset.getClass.getSimpleName)
      .getOrCreate()

    val df:DataFrame = spark.read.json("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/log/jsonoutput/part-00000")
    //DataFrame->Dataset
    //导包,进行相应的编码工作
    import spark.implicits._
    val ds:Dataset[bfGirl] = df.as[bfGirl]
    ds.show()
    spark.stop()
  }
}
case class bfGirl(age:BigInt, faceValue:Double, height:Double, name:String, weight:Double)
//{'age':21,'faceValue':80.9,'height':176.2,'name':'lilydo','weight':51.5}