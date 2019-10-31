package cn.qf.day07.demo02_akka

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable
import scala.concurrent.duration.FiniteDuration
/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class Worker(host: String) extends Actor {
  /**
   * 获得Master进程相应的线程的实例（代理）
   */
  var master: ActorSelection = _

  /**
   * 前处理方法，一个Actor实例只会执行一次
   */
  override def preStart(): Unit = {
    master = context.system.actorSelection("akka.tcp://MasterSystem@127.0.0.1:9999/user/myMaster")

    //向Master发送注册信息
    master ! RegisterInfo(UUID.randomUUID().toString, host, 3, 2)
  }

  /**
   * 会执行多次，执行在要给循环体中。收到了消息后，并与某个case分支吻合，会触发执行。否则，阻塞。
   *
   * @return
   */
  override def receive: Receive = {

    case RegisteredInfo(id: String)=>{
      //启动发送心跳包的定时器
      //导入隐式实例
      import context.dispatcher
      context.system.scheduler.schedule(
        FiniteDuration(0, TimeUnit.SECONDS),
        FiniteDuration(3, TimeUnit.SECONDS),
        self,
        HeatBeat(id)
      )
    }
    //当前worker进程通过定时器,每个三秒发送的心跳包
    case  HeatBeat(id)=>master ! HeatBeat(id)
  }
}

object Worker{
  def apply(host: String): Worker = new Worker(host)
  def main(args: Array[String]): Unit = {
    //步骤：
    val host = "127.0.0.1"
    val port = args(0).trim.toInt
    //①Config
    val str =
      s"""
         |akka.actor.provider=akka.remote.RemoteActorRefProvider
         |akka.remote.netty.tcp.hostname=$host
         |akka.remote.netty.tcp.port =$port
    """.stripMargin
    val config: Config = ConfigFactory.parseString(str)
    //②ActorSystem
    val actorSystem: ActorSystem = ActorSystem.create("WorkerSystem", config)
    //③Actor,且会自动启动线程
    actorSystem.actorOf(Props[Worker](Worker(args(1))), "myWorker")
    //④显示信息
    println("Worker进程启动了...")
  }

}
