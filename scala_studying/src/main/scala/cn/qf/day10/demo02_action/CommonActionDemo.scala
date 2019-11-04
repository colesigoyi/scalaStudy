package cn.qf.day10.demo02_action

import cn.qf.day10.demo01_transformation.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月01日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonActionDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(CommonActionDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    val rdd = sc.parallelize(List(1,2,3,4))
    println("---------reduce:将rdd中所有元素聚合起来-------")
    //reduce:将rdd中所有元素聚合起来
    val sum = rdd.reduce(_+_)
    println(s"sum=$sum")

    println("----------------collect----------------")
    //collect:将多态虚拟机内存中的结果收集到当前虚拟机的内存中存储起来,返回数组
    sc.parallelize(List(("hello",3),("hello",7),("hi",9)))
      .reduceByKey(_ + _)
      .collect()// 建议:在hdfs中存储时建议不要collect,可能会导致内存溢出oom(out of memory)
      .foreach(println)

  }
}
