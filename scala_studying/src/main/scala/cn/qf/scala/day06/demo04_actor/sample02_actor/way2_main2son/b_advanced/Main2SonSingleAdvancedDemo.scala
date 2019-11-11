package cn.qf.scala.day06.demo04_actor.sample02_actor.way2_main2son.b_advanced

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object Main2SonSingleAdvancedDemo {
  def main(args: Array[String]): Unit = {
    //线程实例:由出生状态进入准备状态
    val sonThread = SonActor().start
    sonThread.start()
    //给子线程发消息
    //下述的代码:主线程想子线程单向发送没有返回值的异步消息
    sonThread ! "启动"
    sonThread ! "停止"
  }
}
