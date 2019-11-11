package cn.qf.scala.day04.sample06_modelmatch.demo03_varmatch

/**
 * Description：变量模式<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object VarMatchDemo {
  def main(args: Array[String]): Unit = {
    val name = "张无忌"
    name match {
      case x => println(x)
      case _ => println("no match")
    }
    println("-----------守卫模式-----------")
    name match {
      case x if x.startsWith("张") => println(x)
      case _ => println("no match")
    }
    println("-----------复合模式-----------")
    List(1,2) match {
      case x::2::Nil => println(x)
      case _ => println("no match")
    }
  }
}
