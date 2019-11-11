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

class Person {
  val name:String = "张三丰"
  private var age:Int = _
  private [this] var  address:String = "杭州市"

  /**
   * 下述方法只有在当前类的子类中才能访问
   */
  protected def walk = println("直立行走...")

  def compare(other:Person) = {
//    println(s"当前Person实例的属性address=${this.address},其他Person实例的address属性值是:${other.address}")
    println(s"当前Person实例的属性address=${this.address}")
  }
}
