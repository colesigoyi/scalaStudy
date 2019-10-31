package cn.qf.day06.demo04_actor.sample02_actor.way3_mainandsondouble

import scala.actors.Actor

/**
 * Description：单例线程类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SonActor extends Actor{
  override def act(): Unit = {
    loop {
      react {
        case AsyncMsg(id:Int, msg:String) => {
          println(s"接收到了没有返回值的异步消息,消息码:$id,内容是:$msg")
        }
        case SyncMsg(id:Int, msg:String) => {
          println(s"接收到了有返回值的同步消息,消息码:$id,内容是:$msg")
          Thread.sleep(2000)
          sender ! ReplyMsg(200,"有返回值的同步消息,已经正常处理完毕")
        }
        case WithReturnAsyncMsg(id:Int, msg:String) => {
          println(s"接收到了有返回值的异步消息,消息码:$id,内容是:$msg")
          Thread.sleep(2000)
          sender ! ReplyMsg(200,"有返回值的异步消息,已经正常处理完毕")
        }
      }
    }
  }
}
