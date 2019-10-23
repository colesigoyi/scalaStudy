package cn.qf.day03.sample03_classandobject.demo05_abstract

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

abstract class Vircle {
  /**
   * 抽象属性
   */
  //abstract var name:String
  var name:String

  /**
   * 普通属性
   */
  val speed:Double = 0

  /**
   * 抽象方法
   */
  def drive


  /**
   * 普通方法
   */
  def move = {
    println("交通工具")
  }
}
