package cn.qf.day01.sample06_block

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
object BlockExpressionDemo {
  def main(args: Array[String]): Unit = {
    println("请录入点1的x轴坐标")
    val x1 = StdIn.readDouble()
    println("请录入点1的轴坐标")
    val y1 = StdIn.readDouble()
    println("请录入点2的x轴坐标")
    val x2 = StdIn.readDouble()
    println("请录入点2的轴坐标")
    val y2 = StdIn.readDouble()
//    val distance = {
//     Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2))
//    }
    val distance = {
      val dis = Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2))
      dis //将常量中的值返回
    }
    println(s"平面上($x1 , $y1),($x2 , $y2)两点距离为:$distance")
  }
}
