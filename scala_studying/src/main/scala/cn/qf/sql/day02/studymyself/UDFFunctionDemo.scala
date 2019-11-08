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
object UDFFunctionDemo {
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

    val addName = spark.udf.register("addName",(x:String) => "Name:" + x)
    val addAge = spark.udf.register("addAge",(x:Int) => x + 1)
    df.createOrReplaceTempView("bfGirl")
    spark.sql("select addName(name), age from bfGirl").show()
    /*
    +-----------------+---+
    |UDF:addName(name)|age|
    +-----------------+---+
    |       Name:marry| 18|
    |     Name:lucycat| 20|
    |       Name:Angle| 21|
    |        Name:kate| 21|
    |      Name:lilydo| 21|
    +-----------------+---+
     */
    spark.sql("select name, addAge(age) age from bfGirl").show()
    /*
    +-------+---+
    |   name|age|
    +-------+---+
    |  marry| 19|
    |lucycat| 21|
    |  Angle| 22|
    |   kate| 22|
    | lilydo| 22|
    +-------+---+
     */

    spark.stop()
  }
}
