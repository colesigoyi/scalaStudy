package cn.qf.day05.homework

import scala.io.Source

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Test3 {
  def main(args: Array[String]): Unit = {
    val fileLine = Source.fromFile("log/test.txt").mkString
    val sortedList2 = fileLine.split("\\s+")
      .map((_, 1))
      .groupBy(_._1)
      .mapValues(_.size)
      .toList
      .sortWith(_._2 > _._2)
      .foreach(perEle => println(perEle._1 + "\t" + perEle._2))
  }
}
