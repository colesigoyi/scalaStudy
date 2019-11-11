package cn.qf.scala.day06.homework

import java.io.File

import scala.actors.Actor
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

class Task extends Actor{
  override def act(): Unit = {
    loop {
      react {
        case SubmitTask(fileName) => {
          val countexts = Source.fromFile(new File(fileName)).mkString
          val arr = countexts.split("\r\n")
          val result = arr.flatMap(_.split("\\s+"))
            .map((_, 1))
            .groupBy(_._1)
            .mapValues(_.length)
          sender ! ResultTask(result)
        }
        case StopTask => {
          exit()
        }
      }
    }
  }
}
case class SubmitTask(fileName: String)
case object StopTask
case class ResultTask(result: Map[String, Int])