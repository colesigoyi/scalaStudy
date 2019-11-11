package cn.qf.scala.day03.sample03_classandobject.demo04_constructor

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Student(name:String, age:Int) {

  /**
   *无参的辅助构造器
   */
  def this() = {
    this("",0)//必须调用主构造器或者辅助构造求
  }

  /**
   * 带一个参数的辅助构造器
   * @param name
   */
  def this(name:String) = {
    this(name, 0)
  }

  /**
   * 带两个参数的辅助构造器
   * @param age
   * @param name
   */
  def this(age:Int, name:String) {
    this(name,age)
  }

  override def toString: String = s"Student($name,$age)"
}
