package cn.qf.scala.day01.sample05_ifclause

import scala.io.StdIn

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object IfClauseExercise {
  def main(args: Array[String]): Unit = {
    //val scanner:Scanner = new Scanner(System.in)
    println("请输入星期:")
    val week = StdIn.readLine() //若是方法没有参数可以省略小括号
    val result = {
      if("星期六".equals(week) || "星期日".equals(week)) {
        println("请录入天气温度:")
        val tmp = StdIn.readInt()
        if (tmp >= 30) {
          "去游泳"
        }else{
          "看电影"
        }
      }else{
        println("请录入天气温度:")
        val weather = StdIn.readLine()
        if ("晴天".equals(weather)) {
          "拜访客户"
        }else{
          "留在公司查阅资料"
        }
      }
    }
    println(result)
  }
}
