package cn.qf.day07.demo02_akka

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
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

class Master extends Actor{
  /**
   * 容器，存放所有存活着的Worker进程的信息
   */
  val container: mutable.Map[String, WorkerInfo] = mutable.Map[String, WorkerInfo]()

  /**
   * 前处理方法，一个Actor实例只会执行一次
   */
  override def preStart(): Unit = {
    //导入隐式实例
    import context.dispatcher

    //开启自检定时器（检测容器中哪些Worker进程宕机了）

    //    initialDelay: FiniteDuration : 延迟执行的时间
    //    interval: FiniteDuration： 每次执行的时间
    //    receiver: ActorRef: Actor实例的引用
    //    message: Any 消息
    context.system.scheduler.schedule(
      FiniteDuration(0, TimeUnit.SECONDS),
      FiniteDuration(3, TimeUnit.SECONDS),
      self,
      TimeOut
    )
  }

  /**
   * 会执行多次，执行在要给循环体中。收到了消息后，并与某个case分支吻合，会触发执行。否则，阻塞。
   *
   * @return
   */
  override def receive: Receive = {
    case TimeOut => {
      //当前时间
      val now = System.currentTimeMillis()

      //将那些超时的Worker进程从容器中移除
      container
        .values
        .filter(now - _.lastSendHearBeatTime > CommonEntity.TIME_OUT_SECONDS * 1000)
        .foreach(perEle => container.remove(perEle.id))

      //打印目前容器中存活着的Worker进程的信息
      if (container.nonEmpty)
        println(s"目前，存活着的Worker进程的信息是：${container.values.map(_.host).mkString("、")}")
      else
        println("警告：没有任何Worker进程的信息哦！...")
    }


    case RegisterInfo(id: String, host: String, memory: Int, coreNums: Int) => {
      //构建WorkerInfo的实例，添加到容器中,并向Worker进程反馈信息
      if (!container.contains(id)) {
        container.put(id, WorkerInfo(id, host, memory, coreNums, System.currentTimeMillis()))

        sender ! RegisteredInfo(id)
      }
    }

    case HeatBeat(id) => {
      //更新当前Worker进程信息的最后一次发送心跳包的时间，更新为当前时间
      val nowWorker = container.get(id)
      if (nowWorker != None) {
        nowWorker.get.lastSendHearBeatTime = System.currentTimeMillis
      }
    }
  }
}


object Master extends App {
  //步骤：
  //①Config
  //typesafe是一个框架，可以直接对资源文件(application.properties,application.conf)中的信息进行封装
  val config: Config = ConfigFactory.load
  //②ActorSystem
  val actorSystem: ActorSystem = ActorSystem.create("MasterSystem", config)

  //③Actor,且会自动启动线程
  actorSystem.actorOf(Props[Master], "myMaster")

  //④显示信息
  println("Master进程启动了...")
}
