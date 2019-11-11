package cn.qf.core.day13.homework

import cn.qf.util.{BinarySearchUtil, IP2LongUtil, SparkUtil}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object GetIp {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(GetIp.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //1.2.8.0|1.2.8.255|16910336|16910591|亚洲|中国|北京|北京||中国互联网信息中心|110100|China|CN|116.405285|39.904989
    val ipInfo = sc.textFile("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/ip/ip.txt")
        .map(perLine => {
          val splitLine = perLine.split("\\|")
          val startIp = splitLine(2)
          val endIp = splitLine(3)
          val province = splitLine(6)
          (startIp, endIp, province)
        }).collect()
    //广播变量
    val broadcastIPInfo = sc.broadcast(ipInfo)
    val userInfo = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/ip/http.log")
        .map(perLine => {
          val splitLine = perLine.split("\\|")
          val ip = splitLine(1)
          val ipToLong = IP2LongUtil.ip2Long(ip)
          val arrinfo = broadcastIPInfo.value
          val i = BinarySearchUtil.binarySearch(arrinfo, ipToLong)
          val province = arrinfo(i)._3
          (province, 1)
        })
        .reduceByKey(_ + _)
        .collect()
//      .sortWith(_._2 > _._2)
//      .take(2)
        .foreach(println)

    spark.stop()
  }
}
