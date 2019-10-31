package cn.qf.day06.demo04_actor.sample02_actor.way1_simple

import scala.actors.Actor

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
class MyActor extends Actor {
  /**
   * 下述方法中书写线程体
   */
  override def act(): Unit = {
    for (i <- 1 to 10) {
      println(s"当前的线程名是：${Thread.currentThread.getName}, 循环变量 i = $i")
      Thread.sleep(200)
    }
  }
}
