package cn.qf.scala.day04.sample03_object.demo03_unapply

/**
 * Description：提取器<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Emporor {
  def apply(name: String, dynasty: String) = s"$name--$dynasty"

  /**
   * 提取器:分解apply方法的返回值
   * @param arg
   * @return
   */
  def unapply(arg: String): Option[String] = {
    val name = arg.split("--").head
    if(name.nonEmpty)
      Some(name)//将name放入空列表里
    else
      None
  }
}
