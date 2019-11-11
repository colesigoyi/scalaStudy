package cn.qf.scala.day03.sample03_classandobject.demo01_accessmodifier

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Student extends Person{
  /**
   * 子类中可以访问父类中protected的成员
   */
  def action = super.walk
}
