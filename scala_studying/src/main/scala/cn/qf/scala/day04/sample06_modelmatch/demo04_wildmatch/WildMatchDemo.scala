package cn.qf.scala.day04.sample06_modelmatch.demo04_wildmatch

/**
 * Description：通配符模式匹配<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WildMatchDemo {
  def main(args: Array[String]): Unit = {
    List(1,2,3) match {
      case List(1,x,_) => println(s"匹配到:List(1,$x,_)")
      case List(1,_*) => println(s"匹配到:List(1,_*)")
      case List(2,_*) => println(s"匹配到:List(2,_*)")
      case _ => println("no match")
    }
  }
}
