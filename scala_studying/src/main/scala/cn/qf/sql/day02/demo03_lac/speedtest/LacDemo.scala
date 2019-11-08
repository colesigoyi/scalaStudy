package cn.qf.sql.day02.demo03_lac.speedtest

import org.apache.spark.sql.SparkSession

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月07日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object LacDemo {
  def main(args: Array[String]): Unit = {
    val startTime = System.currentTimeMillis()
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName(LacDemo.getClass.getSimpleName)
      .getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/" +
      "loc/mobilelocation/log")
        .map(perLine => {
          val arr = perLine.split(",")
          val phone = arr(0).trim
          var time = arr(1).trim.toLong
          val lacID = arr(2).trim
          val flg = arr(3).trim.toInt
          if (flg == 1) {
            time = -time
          }
          (phone,time,lacID)
        })
        .toDF("phone","time","lacID")
        .createOrReplaceTempView("tb_log")
    spark.sqlContext.cacheTable("tb_log")
    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/loc/mobilelocation/lac_info.txt")
      .map(perLine => {
        val arr = perLine.split(",")
        val lacID = arr(0).trim
        var W = arr(1).trim
        val E = arr(2).trim
        (lacID, W, E)
      })
      .toDF("lacID","W","E")
      .createOrReplaceTempView("tb_lac")
    //优化:缓存->将经常使用的表缓存起来
    spark.sqlContext.cacheTable("tb_lac")
    spark.sql(
      """
        |select
        | log.phone `手机号码`,
        | lac.lacID `基站ID`,
        | lac.W `经度`,
        | lac.E `纬度`,
        | log.time `时长`
        |from
        |(
            |select *
            |from (
                |select
                |t.phone,
                |t.time,
                |t.lacID,
                |rank() over(partition by phone sort by time desc) rank
                |from (
                    |select
                    |phone,
                    |sum(time) time,
                    |lacID
                    |from tb_log
                    |group by phone,lacID
                |) t
            |) tt
            |where rank <=2
        |) log,
        |tb_lac lac
        |where log.lacID = lac.lacID
        |""".stripMargin).show
    spark.stop()
    val endTime = System.currentTimeMillis()
    println(s"SparkSQL运行时间:${endTime - startTime}ms")
    //SparkSQL运行时间:8145ms
    //SparkSQL运行时间:7989ms
    //SparkSQL运行时间:8288ms
  }
}
