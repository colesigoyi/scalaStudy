package cn.qf.day06.demo03_generic.sample04_downbound

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Pet(name:String) extends Animal {
  override def toString: String = s"Pet($name)"
}
