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

class MyPoint(var x:Double, var y:Double) {
  /**
   * 移动
   * @param xDistance
   * @param yDistance
   */
  def move(xDistance:Double, yDistance:Double) = {
    x += xDistance
    y += yDistance
  }

  override def toString: String = s"MyPoint($x,$y)"
}
