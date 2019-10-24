package cn.qf.day04.sample06_modelmatch.demo07_typematch

import scala.util.Random

/**
 * Description：类型匹配模式<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TypeMatchDemo {
  def main(args: Array[String]): Unit = {
    val arr = Array("李小龙",true,188.9,'男',Double)

    val randomEle = arr(Random.nextInt(arr.length))

    val result = getResult(randomEle)
  }

  private def getResult(randomEle: Any) = {
    randomEle match {
      case name: String => println(name)
      case idMarried: Boolean => println(idMarried)
      case hight: Double => println(hight)
      case gender: Char => println(gender)
      case Double => println(Double)
      case _ => println("no match")
    }
  }
}
