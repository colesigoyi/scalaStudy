package cn.qf.scala.day06.demo03_generic.sample02_nibian

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

abstract class Printer[-A] {
  def show(a:A)
}
