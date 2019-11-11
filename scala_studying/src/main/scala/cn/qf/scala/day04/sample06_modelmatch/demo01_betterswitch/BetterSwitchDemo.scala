package cn.qf.scala.day04.sample06_modelmatch.demo01_betterswitch

import scala.io.StdIn

/**
 * Description：更好的switch<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object BetterSwitchDemo {
  def main(args: Array[String]): Unit = {
    //需求:使用模式匹配语句完成计算机
    //1.接受控制台录入的操作符,操作数
    println("请录入运算符(+,-,*,/):")
    val flg = StdIn.readChar()
    println("请录入运算数1:")
    val num1 = StdIn.readDouble()
    println("请录入运算数2:")
    val num2 = StdIn.readDouble()

    //2.使用模式匹配语句进行分别计算
    val result = flg match {
      case '+' => num1 + num2
      case '-' => num1 - num2
      case '*' => num1 * num2
      case '/' => num1 / num2
      case _ => "输入有误!"
    }
    //3.显示结果
    if (result.isInstanceOf[String]) {
      println(result)
    }else
      println(s"$num1 $flg $num2 = $result")

  }
}
