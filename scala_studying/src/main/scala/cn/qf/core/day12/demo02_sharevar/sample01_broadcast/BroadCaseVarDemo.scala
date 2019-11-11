package cn.qf.core.day12.demo02_sharevar.sample01_broadcast

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object BroadCaseVarDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(BroadCaseVarDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext
    //需求:容器中存储了不同学生的年龄,过完年,都增加了1岁
    val rdd = sc.parallelize(Seq(19,20, 21))
    //在Driver进程中准备一个共同的常量,分发到Executor进程中
    val incrementAge = 1

    //情形1:没有使用广播变量
    rdd.map(_ + incrementAge).foreach(println)
    println("------------------------------------")
    //情形2:使用广播变量
    //1.将变量封装到广播变量中
    val bcAge = sc.broadcast[Int](incrementAge)
    //每个Executor进程共通2的存储空间存放的来自Driver进程中的变量incrementAge
    //通过广播变量实例.value方式读取出来
    rdd.map(_+bcAge.value).foreach(println)

    spark.stop()
  }
}
