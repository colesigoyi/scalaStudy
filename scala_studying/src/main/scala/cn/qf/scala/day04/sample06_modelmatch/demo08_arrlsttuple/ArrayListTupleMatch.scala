package cn.qf.scala.day04.sample06_modelmatch.demo08_arrlsttuple

/**
 * Description：匹配模式<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ArrayListTupleMatch {
  def main(args: Array[String]): Unit = {
    //数组
    Array(1,2,3) match {
      case Array(1,2) => println("与Array(1,2)匹配")
      case Array(1,_*) => println("与Array(1,_*)匹配")
      case Array(_*) => println("与Array(_*)匹配")
      case _ => println("no match")
    }
    println("---------------------")
    //列表
    List(1,2,3) match {
      case 1 :: 2 :: Nil => println("与1 :: 2 :: Nil匹配")
      case 1 :: tail => println("与1 :: tail匹配")//tail在此处代表List子列表
      case List(_*) => println("与List(_*)匹配")
      case _ => println("no match")
    }
    println("---------------------")
    //元组
    (1,2,3) match {
      case (1,2,z) => println("与(1,2,z)匹配")
      case (1,_,_) => println("与(1,_,_)匹配")//不支持*通配符
      case (1,3,5) => println("与(1,3,5)匹配")
      case _ => println("no match")
    }
  }
}
