package cn.qf.scala.day04.sample03_object.demo01_partern

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

/**
 * 下述的Person类时Person单例对象的半生类
 */
class Person {
  private val name = "jack"
  def show = println(s"名字是$name ,地址:${Person.address}")
}

/**
 * 下面的单例类是上面Person类的伴生对象
 */
object Person {
  private val address = "杭州"

  /**
   * 下述的方法,p.show调用了伴生类中的私有的方法
   * @param p
   */
  def doSomething(p:Person) = p.show
}