package cn.qf.scala.day06.homework

import java.io.{File, PrintWriter}

import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object getWords {


  val word = ListBuffer[String]()
  def main(args: Array[String]): Unit = {
    val words = ListBuffer("你好","hello","谢谢","欢迎","jack","marry","任务","学习","用户","统计","低延迟","spark")
    for (i <- 1 to 1000) {
      val nums = scala.util.Random.nextInt(words.length)
      word += words(nums) + " "
      if (i % 10 == 0) {
        word += "\n"
      }
    }

    val pw:PrintWriter = new PrintWriter(new File("homeworktest/word.txt"))
    pw.println(word.mkString)
    pw.flush()
    pw.close()
  }
}
