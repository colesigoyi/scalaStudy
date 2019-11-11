package cn.qf.scala.day04.sample02_singleobj

/**
 * Description：单例对象<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object President {
  val name:String = "特朗普"
  def show = println(s"当前的总统是:$name")
}
