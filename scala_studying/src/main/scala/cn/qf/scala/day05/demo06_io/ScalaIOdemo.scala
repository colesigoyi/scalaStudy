package cn.qf.scala.day05.demo06_io

import java.io.{File, PrintWriter}

import scala.collection.mutable.ArrayBuffer
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
object ScalaIOdemo {
  def main(args: Array[String]): Unit = {
    //fromFile(文件的路径) -> 读取磁盘文件内容到内存中
    val source = Source.fromFile("log/info.txt")
    for (perChar <- source) {
      print(perChar)
    }
    source.close()
    println("---------------------------------")
    //getLines -> 返回迭代器,迭代器中的每个元素是文件中的每行内容
    for (perLine <- Source.fromFile("log/info.txt").getLines())
      println(perLine)

    println("---------------------------------")
    //mkString -> 将文件中的内容读取到字符串中存储
    val fileContent = Source.fromFile("log/info.txt").mkString
    println("mkString\n\n" + fileContent)

    println("---------------------------------")
    val fileLine = Source.fromFile("log/info.txt").mkString
    val sortedList2 = fileLine.split("\\s+")
      .map((_, 1))
      .groupBy(_._1)
      .mapValues(_.size)
      .toList
      .sortWith(_._2 > _._2)
      .foreach(perEle => println(perEle._1 + "\t" + perEle._2))
    println("---------------------------------")
    //fromURL -> 读取参数指定的资源到内存中
    val pageContent = Source.fromURL("https://www.baidu.com/?tn=02003390_43_hao_pg")
      .mkString
    val pw:PrintWriter = new PrintWriter(new File("log/baidu.html"))
    pw.println(pageContent)
    pw.flush()
    pw.close()
  }
}
