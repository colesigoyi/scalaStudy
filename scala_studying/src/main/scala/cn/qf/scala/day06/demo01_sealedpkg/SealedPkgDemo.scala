package cn.qf.scala.day06.demo01_sealedpkg

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SealedPkgDemo {
  def main(args: Array[String]): Unit = {
    val factor = 4
    //下述的赋值式的有名函数,函数体中使用了外部的变量(或者常量),与本函数自带的形参组合成一个整体,即为闭包
    val f = (x:Int) => x * factor

    println(s"${f(5)}")
  }
}
