package cn.qf.scala.day03.sample03_classandobject.demo05_abstract

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Bus extends Vircle {
  /**
   * 抽象属性
   */
  override var name: String = "公交车"

  /**
   * 抽象方法
   */
  override def drive: Unit = println("公交车在地上行驶")

  /**
   * 普通方法
   */
  override def move: Unit = super.move
  /**
   * 普通属性
   */
  override val speed:Double = 100
}
