package cn.qf.scala.day06.demo02_implicit.sample03_paremandobject.b_advanced

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MyPredef {
  /**
   * 准备了隐式实例
   */
  implicit val instance = new Cal[Int] {
    /**
     * 求和的一个抽象方法
     *
     * @param x
     * @param y
     * @return
     */
    override def add(x: Int, y: Int): Int = x + y
  }
}
