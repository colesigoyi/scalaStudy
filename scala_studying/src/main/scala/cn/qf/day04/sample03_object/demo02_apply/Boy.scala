package cn.qf.day04.sample03_object.demo02_apply

/**
 * Description：伴生对象,伴生类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Boy(name:String, age:Int) {
  override def toString: String = s"Boy($name ,$age)"
}
object Boy {
  /**
   * 伴生对象中的apply方法
   * @param name
   * @param age
   * @return
   */
  def apply(name: String, age: Int): Boy = new Boy(name, age)

  /**
   * 与上一个apply实现重载
   * @param name
   * @return
   */
  def apply(name: String): Boy = new Boy(name, 0)
}
