package cn.qf.core.day14.demo03_cdn.sample03_flow

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月06日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object FlowDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(FlowDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    //正则表达式
//    val legalFlowRegex =
//      """
//        |.*(\d{2}/\w{3}/\d{4}:\d{2}).*(200|206|304)\s(\d+)\s.*
//        |""".stripMargin
      val legalFlowRegex = """.*(\d{2}/\w{3}/\d{4}:\d{2}).*(200|206|304)\s(\d+)\s.*""".r

    //方案1:小时要确定到年份月份天小时
//    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
//      "09_scala/scala_studying/cdn/cdn.txt")
//      .filter(_.matches(legalFlowRegex.regex))
//      //.foreach(println)
//      .map {
//        case legalFlowRegex(time, status, flow) => (time, flow.toLong)
//        case _ => ("", 0L)
//      }.filter(tuple => tuple._1.trim != "" && tuple._2 != 0L)
//        .reduceByKey(_ + _)
//        .foreach(perEle => {
//          println(f"时间:${perEle._1}, ${perEle._2.toDouble/(1024*1024)%.2f}M" )
//        })
    //方案2:将所有的年份,月份,天对应的小时聚合起来(RDD中最终有24个元素)
    sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/cdn/cdn.txt")
      .filter(_.matches(legalFlowRegex.regex))
      //.foreach(println)
      .map {
        case legalFlowRegex(time, status, flow) => (time.substring(time.lastIndexOf(":")+1), flow.toLong)
        case _ => ("", 0L)
      }.filter(tuple => tuple._1.trim != "" && tuple._2 != 0L)
      .reduceByKey(_ + _)
      .sortByKey(ascending = true, 1)
        //.foreach(println)
    /*
    (14,59960217443)
    (06,12527393563)
    (09,56501241931)
    (19,55761802022)
    (21,57704125206)
    (20,59488118262)
    (15,48814835271)
    (04,3530715436)
    (18,48675169969)
    (02,5578881085)
    (00,16555690182)
    (03,3905097951)
    (12,50467274149)
    (16,49334299327)
    (17,48121921811)
    (22,45163141080)
    (05,5380366369)
    (07,23633011105)
    (11,49235692494)
    (13,54782508597)
    (08,46418037074)
    (01,4193122972)
    (23,26868763427)
    (10,66015209768)
     */
      .foreach(perEle => {
        println(f"时间:${perEle._1}, ${perEle._2.toDouble/(1024*1024*1024)}%.2fG" )
      })
    /*
    时间:00, 15.42G
    时间:01, 3.91G
    时间:02, 5.20G
    时间:03, 3.64G
    时间:04, 3.29G
    时间:05, 5.01G
    时间:06, 11.67G
    时间:07, 22.01G
    时间:08, 43.23G
    时间:09, 52.62G
    时间:10, 61.48G
    时间:11, 45.85G
    时间:12, 47.00G
    时间:13, 51.02G
    时间:14, 55.84G
    时间:15, 45.46G
    时间:16, 45.95G
    时间:17, 44.82G
    时间:18, 45.33G
    时间:19, 51.93G
    时间:20, 55.40G
    时间:21, 53.74G
    时间:22, 42.06G
    时间:23, 25.02G
     */
    spark.stop()
  }
}
