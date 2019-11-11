package cn.qf.scala.day04.sample03_object.demo02_apply

/**
 * Description：伴生对象,伴生类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Girl {
  def apply(hobbies:Array[String]) = println(s"爱好是:${hobbies.toBuffer}")
}
