package cn.qf.scala.day05.demo02_option

import scala.collection.mutable
import scala.io.StdIn

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object OptionDemo {
  def main(args: Array[String]): Unit = {
    //需求:使用map容器存储学生的名字以及分数,根据名字查询学生的考分
    val container = Map("张无忌"->98,"张三丰"->67)
    println("请录入待查询的学生姓名:")
    val name = StdIn.readLine()
    //方式1:Option方法
    val s = getScore(container, name,0)
    if (s==0)
      println("学生没有参加考试")
    else
      println(s"学生[${name}]的分数是:$s")
    println("-----------优化:getOrElse------------")
    //方式2:使用getOrElse方式
    val score = container.getOrElse(name,0)
    if (s==0)
      println("学生没有参加考试")
    else
      println(s"学生[${name}]的分数是:$score")
  }

  private def getScore(container: Map[String, Int], name: String, s:Int) = {
    container.get(name) match {
      case Some(score) => score
      case None => s
    }
  }
}
