package cn.qf.scala.day01.sample07_circle.demo2_while

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WhileCircleDemo1 {
  def main(args: Array[String]): Unit = {
    var sum = 0
    var i = 0
    while(i < 10){
    i += 1
    sum += i
  }
    println(s"1+2+3...+10=$sum")
    println("-----------------------------")

    do{
      i += 1
      sum += i
    } while (i < 10)
    println(s"1+2+3...+10=$sum")
    println("-----------------------------")

  }
}