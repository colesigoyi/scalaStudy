package cn.qf.day06.demo04_actor.sample02_actor.way3_mainandsondouble

import scala.actors.threadpool.Future

/**
 * Description：主线程与子线程双向通信演示<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MainAndSonDoubleDemo {
  def main(args: Array[String]): Unit = {
    //前提:构建线程的实例并启动
    SonActor.start
    //测试:①主线程向子线程发送一个没有返回值的异步消息
//    SonActor ! AsyncMsg(1, "启动,开始工作")
//    println("------------------------------")
    //测试:②主线程向子线程发送一个有返回值的同步消息
//    val msg = SonActor !? SyncMsg(2,"进展如何?")
//    println(s"接收到子线程返回的消息是:$msg")
//    println("------------------------------")
    //测试:③有返回值的异步消息
    val result:actors.Future[Any] = SonActor !! WithReturnAsyncMsg(3,"工作进展反馈")
    //方式1：强行等待
//    while (!result.isSet) {//每次循环,判断子进程有没有执行完毕,若是执行完毕,就退出循环
//    }
//    println(s"返回值是:${result.apply()}")//获得子进程的返回值
    //方式2:apply方式,本质是异步有返回值, 变成了同步有返回值
    val returnValue = result.apply()//调用apply之后，主线程让出cpu的时间片，让子线程执行，子执行完毕后，主线程接着执行后续的逻辑
    println(s"返回的结果是：$returnValue")
  }
}
