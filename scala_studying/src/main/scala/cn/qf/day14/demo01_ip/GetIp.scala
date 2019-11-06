package cn.qf.day14.demo01_ip

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

    //1.读取ip.txt,装载到rdd中,每个元素形如:(开始ip对应的1ong,结束ip对应的1ong,省份名)
    val ipInfo = sc.textFile("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/ip/ip.txt")
        .map(perLine => {
          val splitLine = perLine.split("\\|")
          val startIp = splitLine(2)
          val endIp = splitLine(3)
          val province = splitLine(6)
          (startIp, endIp, province)
        }).sortBy(_._2, ascending = true, 1)
    //广播变量
    //2.将上述的RDD收集为Array,封装到广播变量中
    val arr = ipInfo.collect()
    val broadcastIPInfo = sc.broadcast(arr)
    //3.读取http.log日志信息,装载到rdd中,分析,将每个元素变形为(省份名,1)
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
      //3.reduceByKey,输出→该网站在不同省份访问量的情况
        .reduceByKey(_ + _)
        .collect()
        .sortWith(_._2 > _._2)
//      .take(2)
        .foreach(println)

    spark.stop()
  }
}
