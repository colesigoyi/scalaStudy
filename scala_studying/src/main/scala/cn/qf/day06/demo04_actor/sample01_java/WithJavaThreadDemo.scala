package cn.qf.day06.demo04_actor.sample01_java

/**
 * Description：多线程编程实现方式1之java方式<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WithJavaThreadDemo {
  def main(args: Array[String]): Unit = {
    println(s"main方法中,当前的线程名是:${Thread.currentThread().getName}")
    new MyThread().start()
  }
}
