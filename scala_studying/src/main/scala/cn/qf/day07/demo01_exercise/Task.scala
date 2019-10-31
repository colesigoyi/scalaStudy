package cn.qf.day07.demo01_exercise

import scala.actors.Actor
import scala.io.Source

/**
 * Description：领受任务(计算待定文件中单词出现的次数,并返回结果)线程类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Task extends Actor{
  override def act(): Unit = {
    loop {
      react {
        case SubmitTask(fileName:String) => {
          //计算出现次数
          val result:Map[String, Int] = Source.fromFile(fileName)
            .mkString
            .split("\\s+")
            .map((_, 1))
            .groupBy(_._1)
            .mapValues(_.size)

          //返回
          sender ! ReturnResult(result)
        }
      }
    }
  }
}
