package cn.qf.scala.day05.homework

import scala.io.Source
import java.io.File

import scala.collection.mutable.ListBuffer


/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object HomeWork {
  def main(args: Array[String]): Unit = {
    val fileContent = ListBuffer[String]()
    getFileContent(fileContent)
    getWordNums(fileContent)
  }
  def getFileContent(fileContent: ListBuffer[String]) = {
    for (d <- subDir(new File("homeworktest"))) {
      fileContent += Source.fromFile(d.getPath).mkString + " "
    }
  }
  def getWordNums(fileContent: ListBuffer[String]) = {
    fileContent.mkString.split("\\s+")
      .map((_, 1))
      .groupBy(_._1)
      .mapValues(_.size)
      .toList
      .sortWith(_._2 > _._2)
      .foreach(perEle => println(perEle._1 + "\t" + perEle._2))
  }
  def subDir(dir:File):Iterator[File] ={
    val dirs = dir.listFiles().filter(_.isDirectory())
    val files = dir.listFiles().filter(_.isFile())
    files.toIterator ++ dirs.toIterator.flatMap(subDir _)
  }
}
