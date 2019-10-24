package cn.qf.day04.sample06_modelmatch.demo01_betterswitch

import scala.io.StdIn

/**
 * Description：更好的switch(方法抽取)<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object BetterSwitchDemo2 {
  def main(args: Array[String]): Unit = {
    //需求:使用模式匹配语句完成计算机
    //1.接受控制台录入的操作符,操作数
    val (flg: Char, num1: Double, num2: Double) = getInputInfo
    //2.使用模式匹配语句进行分别计算
    val result = getResult(flg, num1, num2)
    //3.显示结果
    show(flg, num1, num2, result)

  }
  private def getResult(flg: Char, num1: Double, num2: Double) = {
    flg match {
      case '+' => num1 + num2
      case '-' => num1 - num2
      case '*' => num1 * num2
      case '/' => num1 / num2
      case _ => "输入有误!"
    }
  }
  private def show(flg: Char, num1: Double, num2: Double, result: Any) = {
    if (result.isInstanceOf[String]) {
      println(result)
    } else
      println(s"$num1 $flg $num2 = $result")
  }
  private def getInputInfo = {
    println("请录入运算符(+,-,*,/):")
    val flg = StdIn.readChar()
    println("请录入运算数1:")
    val num1 = StdIn.readDouble()
    println("请录入运算数2:")
    val num2 = StdIn.readDouble()
    (flg, num1, num2)
  }
}
