package cn.qf.scala.day04.sample01_trait.demo01_sameprogram

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
trait Pet {
  val name : String
  override def toString: String = name//重写toString方法
}
