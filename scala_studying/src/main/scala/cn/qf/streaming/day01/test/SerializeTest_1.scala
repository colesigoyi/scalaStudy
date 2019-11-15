package cn.qf.streaming.day01.test

import java.net.InetAddress

import cn.qf.util.SparkUtil
import org.apache.hadoop.util.MachineList.InetAddressFactory
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月11日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SerializeTest_1 {
  def main(args: Array[String]): Unit = {
    val conf = SparkUtil.getSparkConf
    val sc = new SparkContext(conf)
    val lines = sc.parallelize(Array("xiaolv", "xiaohong", "xiaoming"))
    //map方法中的函数是在Executor的某个Task中执行的
    val res = lines.map(x => {
      val rules = new Rules
      val hostname = InetAddress.getLocalHost.getHostName
      val threadName = Thread.currentThread().getName
      (hostname, threadName, rules.rulesMap.getOrElse(x, 0), rules.toString)
    })
    println(res.collect.toBuffer)
    /*
    ArrayBuffer(
    (localhost,Executor task launch worker for task 0,0,cn.qf.streaming.day01.test.Rules@5c3d762c),
    (localhost,Executor task launch worker for task 1,0,cn.qf.streaming.day01.test.Rules@736d5f3b),
    (localhost,Executor task launch worker for task 1,26,cn.qf.streaming.day01.test.Rules@374cd5ba))
     */
    sc.stop()
  }
}
