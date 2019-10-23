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

object AbstractClassDemo {
  def main(args: Array[String]): Unit = {
    val bus:Vircle = new Bus  //父类的引用指向子类的实例
    bus.drive
    println("-----------------------")
    new Vircle { //抽象类匿名的子类实例
      /**
       * 抽象属性
       */
      override var name: String = "地铁"

      /**
       * 抽象方法
       */
      override def drive: Unit = println("地铁在地下行驶")
    }.drive
  }
}
