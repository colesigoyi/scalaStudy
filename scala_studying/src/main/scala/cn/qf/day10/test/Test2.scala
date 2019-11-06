package cn.qf.day10.test

import cn.qf.util.SparkUtil
import org.apache.spark.SparkContext

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月01日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Test2 {
  def main(args: Array[String]): Unit = {
    val sc = SparkUtil.getSparkSession(Test2.getClass.getName, "local[*]").sparkContext

    val contextLine = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
      "09_scala/scala_studying/log/testdata.txt").filter(line => line.contains("Spark"))
    println("包含\"Spark\"的句子是:")
    contextLine.foreach(println)

    val nums = contextLine.count() // 统计行数
    println(s"包含Spark的行数总数为:$nums")

    val contextLine2 = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
         "09_scala/scala_studying/log/testdata.txt")
      .map(line => line.split(" ").length)

      val nums2 = contextLine2.reduce((a, b) => if (a > b) a else b)
    //contextLine2.foreach(println)
    println(s"最多的一行包含:$nums2 个单词")

        val context = sc.textFile("file:////Users/taoxuefeng/Documents/02_StudyCoding/" +
          "09_scala/scala_studying/log/testdata.txt")
          val a = context.filter(line => line.contains("a"))
          val b = context.filter(line => line.contains("b"))
          val ab = context.filter(line => line.contains("b") && line.contains("b"))
        println("包含\"a\"的句子是:")
        a.foreach(println)
//
        val anums = a.count() // 统计行数
        println(s"包含b的行数总数为:$anums")
        println("-----------------------------------------")
        println("包含\"b\"的句子是:")
        b.foreach(println)

        val bnums = b.count() // 统计行数
        println(s"包含b的行数总数为:$bnums")
    joinRdd(sc)
  }
  def joinRdd(sc:SparkContext) {
    val name= Array((1,"spark"),(2,"flink"),(3,"hadoop"))
    val score= Array((1,100),(2,90),(3,80))
    val namerdd=sc.parallelize(name)
    val scorerdd=sc.parallelize(score)
    val result = namerdd.join(scorerdd)
    result.collect.foreach(println)
  }
}
