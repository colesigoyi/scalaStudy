package cn.qf.streaming.day01.test

import java.net.InetAddress

import cn.qf.util.SparkUtil
import org.apache.spark.SparkContext

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月11日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SerializeTest_4 {
  def main(args: Array[String]): Unit = {
    val conf = SparkUtil.getSparkConf
    val sc = new SparkContext(conf)
    val lines = sc.parallelize(Array("xiaolv", "xiaohong", "xiaoming"))
    //该对象在Driver中创建单例对象

    val res = lines.map(x => {
      val hostname = InetAddress.getLocalHost.getHostName
      val threadName = Thread.currentThread().getName
      /*
      不用在Driver端去创建对象，Rules不用实现序列化
       */
      (hostname, threadName, ObjectRules.rulesMap.getOrElse(x, 0), ObjectRules.toString)
    })
    println(res.collect.toBuffer)
    /*
    ArrayBuffer(
    (localhost,Executor task launch worker for task 0,0,cn.qf.streaming.day01.test.ObjectRules$@2539fca6),
    (localhost,Executor task launch worker for task 1,0,cn.qf.streaming.day01.test.ObjectRules$@2539fca6),
    (localhost,Executor task launch worker for task 1,0,cn.qf.streaming.day01.test.ObjectRules$@2539fca6))
     */
    sc.stop()
  }
}
