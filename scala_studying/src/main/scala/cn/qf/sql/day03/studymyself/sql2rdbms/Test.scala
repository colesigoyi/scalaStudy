package cn.qf.sql.day03.studymyself.sql2rdbms

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
object Test {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(Test.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //1.2.8.0|1.2.8.255|16910336|16910591|亚洲|中国|北京|北京||中国互联网信息中心|110100|China|CN|116.405285|39.904989
    val ipInfo = sc.textFile("file:////Users/taoxuefeng/Documents/" +
      "02_StudyCoding/09_scala/scala_studying/ip/ip.txt")
      println(s"分区数${ipInfo.getNumPartitions}")
    val num = ipInfo.coalesce(1).getNumPartitions//合并操作
    println(s"${num}")
  }
}
