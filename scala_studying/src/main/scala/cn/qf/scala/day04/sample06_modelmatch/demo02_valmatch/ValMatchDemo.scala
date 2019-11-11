package cn.qf.scala.day04.sample06_modelmatch.demo02_valmatch

import scala.util.Random

/**
 * Description：常量模式<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ValMatchDemo {
  val SINA = "www.sina.com"
  val SOHU = "www.souhu.com"
  def main(args: Array[String]): Unit = {
    val arr = Array(SINA, SOHU)
    val randomWebName = arr(Random.nextInt(arr.length))
    //常量字面量
    val result = getResult(randomWebName)
    val result2 = getResult2(randomWebName)
    println(s"结果是: $result")
    println(s"结果是: $result2")
  }
  /**
   * 常量字面量
   * @param randomWebName
   * @return
   */
  private def getResult(randomWebName: String) = {
    randomWebName match {
      case "www.sina.com" => "新浪 => www.sina.com"
      case "www.souhu.com" => "搜狐 => www.sohu.com"
      case _ => "没有匹配上"
    }
  }
  /**
   * 常量变量
   * @param randomWebName
   * @return
   */
  private def getResult2(randomWebName: String) = {
    randomWebName match {
      case SINA => "新浪 => www.sina.com"
      case SOHU => "搜狐 => www.sohu.com"
      case _ => "没有匹配上"
    }
  }
}
