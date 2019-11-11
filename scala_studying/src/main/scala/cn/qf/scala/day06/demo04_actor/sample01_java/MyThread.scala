package cn.qf.scala.day06.demo04_actor.sample01_java

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class MyThread extends Thread{
  /**
   *线程体
   */
  override def run(): Unit = {
    println(s"当前线程的名字是:${Thread.currentThread().getName}")

  }
}
