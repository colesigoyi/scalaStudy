package cn.qf.sql.day03.sample01_udaf

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月08日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object UDAFDemo {
  def main(args: Array[String]): Unit = {
    //SparkSession

    val spark = SparkUtil.getSparkSession(
      UDAFDemo.getClass.getName,
      "local[*]"
    )
    import spark.implicits._

    val ds = spark.read.json("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/emp/employees.json")
        .as[Emp]
    val tc = new MyAvg().toColumn.name("->平均薪资<-")

    ds.select(tc)
        .show
    /*
    +------------------+
    |    ->平均薪资<-   |
    +------------------+
    |21666.666666666668|
    +------------------+
     */

    spark.stop()
  }
}
