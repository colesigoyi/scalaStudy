package cn.qf.scala.day05.demo01_sealed

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object SealedClassDemo {
  def main(args: Array[String]): Unit = {
    val chair = 椅子()
    println(findPositionToRest(chair))

    val couch = 沙发()
    println(findPositionToRest(couch))
  }
  def findPositionToRest(f:家具): String = f match {
    case c:椅子 => "在椅子上休息"
    case s:沙发 => "在沙发上休息"
    //case _ => "no match"
  }
}
