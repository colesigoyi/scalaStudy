package cn.qf.scala.day05.demo03_partialfunction

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
object PartialFunctionDemo {
  def main(args: Array[String]): Unit = {
    println("请录入当前的季节:")
    println(s"存在'o'的匹配结果吗:${getActionname.isDefinedAt('o')}")

//    val season = StdIn.readChar()
//    val actionname = getActionname(season)
    val actionname = getActionname( StdIn.readChar())
//    println(s"当前季节是:$season , 活动是$actionname")
    println(s"活动是$actionname")
  }
  /**
   * 根据不同的季节返回从事的活动
   * 偏函数模式匹配
   * @return
   */

  def getActionname:PartialFunction[Char,String] = {
    case '春' => "春天去踏青"
    case '夏' => "夏天去游泳"
    case '秋' => "秋天去采摘"
    case '冬' => "冬天去冬眠"
    case _ => "警告:录入有误!"
  }

  /**
   * 传统的模式匹配
   */
  def getActionname2(season:Char):String =season match {
    case '春' => "春天去踏青"
    case '夏' => "夏天去游泳"
    case '秋' => "秋天去采摘"
    case '冬' => "冬天去冬眠"
    case _ => "警告:录入有误!"
  }
}
