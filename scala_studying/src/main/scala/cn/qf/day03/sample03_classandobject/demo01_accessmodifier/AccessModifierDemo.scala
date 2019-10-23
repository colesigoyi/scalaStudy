package cn.qf.day03.sample03_classandobject.demo01_accessmodifier

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object AccessModifierDemo {
  def main(args: Array[String]): Unit = {
    //测试
    val person = new Person
    println(s"名字:${person.name}")
    val student = new Student
    student.action
  }
}
