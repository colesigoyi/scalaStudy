package cn.qf.day04.sample06_modelmatch.demo02_valmatch

import scala.util.Random

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ValMatchDemo {
  val sina = "www.sina.com"
  val souhu = "www.souhu.com"
  def main(args: Array[String]): Unit = {
    val arr = Array(sina, souhu)
    val randomWebName = arr(Random.nextInt(arr.length))
    //常量字面量
    val result = getResult(randomWebName)
    val result2 = getResult2(randomWebName)
    println(s"结果是: $result2")
  }
  private def getResult(randomWebName: String) = {
    randomWebName match {
      case "www.sina.com" => "新浪 => www.sina.com"
      case "www.souhu.com" => "搜狐 => www.sohu.com"
      case _ => "没有匹配上"
    }
  }
  private def getResult2(randomWebName: String) = {
    randomWebName match {
      case sina => "新浪 => www.sina.com"
      case sohu => "搜狐 => www.sohu.com"
      case _ => "没有匹配上"
    }
  }
}
