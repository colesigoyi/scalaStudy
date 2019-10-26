package cn.qf.day05.homework

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
object Test2 {
  def main(args: Array[String]): Unit = {
    //需求:使用模式匹配语句完成计算机
    //1.接受控制台录入的操作符,操作数
    println("请录入数字(1,2,3):")
    val num = StdIn.readInt()

    //2.使用模式匹配语句进行分别计算
    val result = num match {
      case 1 => "red"
      case 2 => "green"
      case 3 => "yellow"
      case _ => "输入有误!"
    }
    //3.显示结果
    println(s"$result")
  }
}
