package cn.qf.day01.sample04_operatesymbol

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object OperateSymbolDemo {
  def main(args: Array[String]): Unit = {
    val num1, num2:Int = 3
    val sum = num1 + num2
    println(s"sum = $sum")

    println("-------------------------")

    //可以简化为 val sum2 = num1 + num2,语法现象称之为grammer sugar语法糖现象
    // +是一个方法
    val sum2 = num1.+(num2)
    println(s"sum2 = $sum2")
  }
}
