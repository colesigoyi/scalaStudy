package cn.qf.sql.day02.demo03_lac.speedtest

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月01日
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object LocationExerciseDemo {
  def main(args: Array[String]): Unit = {
    val startTime = System.currentTimeMillis()
    val sc = SparkUtil.getSparkSession(LocationExerciseDemo.getClass.getName, "local[*]").sparkContext
    val phoneRDD = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/" +
      "loc/mobilelocation/log")
      .map(perLine => {
        val arr = perLine.split(",")
        val phoneNum = arr(0).trim
        val tmpTime = arr(1).trim.toLong
        val lacId = arr(2).trim
        val flag = arr(3).trim.toInt
        val time = if (flag == 1) -tmpTime else tmpTime
        ((phoneNum, lacId), time)
      }).reduceByKey(_ + _)
      .groupBy(_._1._1)
      .mapValues(_.toList.sortBy(_._2).reverse.take(2))
      .map(_._2)
      .flatMap(perEle => perEle)
      .map(perEle => ((perEle._1._2), ((perEle._1._1), (perEle._2))))
    val lacRDD = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/09_scala/scala_studying/" +
      "loc/mobilelocation/lac_info.txt")
      .map(perLine => {
        val arr = perLine.split(",")
        val lacId = arr(0).trim
        val W = arr(1).trim
        val N = arr(2).trim
        (lacId,(W, N))
      })
    phoneRDD.join(lacRDD)
      .map(per=>(per._2._1._1,per._2._2,per._2._1._2))
      .foreach(perEle=>println(s"手机号码: ${perEle._1},经纬度: ${perEle._2},停留时长: ${perEle._3}"))
    sc.stop()
    val endTime = System.currentTimeMillis()
    println(s"SparkCore运行时间:${endTime - startTime}ms")
    //SparkCore运行时间:5248ms
    //SparkCore运行时间:4537ms
    //SparkCore运行时间:4639ms
  }
}
