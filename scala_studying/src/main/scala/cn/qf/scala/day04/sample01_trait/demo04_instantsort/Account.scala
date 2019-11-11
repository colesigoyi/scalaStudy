package cn.qf.scala.day04.sample01_trait.demo04_instantsort

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

abstract class Account {
  println("Account类的实例被创建")
  /**
   * 余额
   */
  var balance: Double = 2000
}
