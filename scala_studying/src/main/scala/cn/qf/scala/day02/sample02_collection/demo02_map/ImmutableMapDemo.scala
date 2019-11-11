package cn.qf.scala.day02.sample02_collection.demo02_map

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ImmutableMapDemo {
  def main(args: Array[String]): Unit = {
    val container = Map("jack" -> 90,"tom" -> 88, "marry" -> 91)
    //测试更新
    //container("jack") = 100

    val newContainer = container ++ Map("kate" -> 77)
    println(s"原来的集合是:$container")
    println(s"现在的集合是:$newContainer")

    //查询
    println(s"jack的成绩是:${container.getOrElse("jack",0)}")
    //遍历
    println("---------------元组遍历--------------")
    for ((key, value) <- container)
      println(s"${key}的成绩是$value")

    println("---------------下标遍历--------------")
    for (key <- container.keys)
      println(s"${key}的成绩是${container(key)}")
  }
}
