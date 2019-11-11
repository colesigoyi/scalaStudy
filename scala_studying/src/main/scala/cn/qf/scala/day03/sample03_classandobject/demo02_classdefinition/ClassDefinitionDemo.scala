package cn.qf.scala.day03.sample03_classandobject.demo02_classdefinition

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object ClassDefinitionDemo {
  def main(args: Array[String]): Unit = {
    val point = new MyPoint(10,20)
    println(s"移动前:$point")
    point.move(11,33)
    println(s"移动后:$point")
  }
}
