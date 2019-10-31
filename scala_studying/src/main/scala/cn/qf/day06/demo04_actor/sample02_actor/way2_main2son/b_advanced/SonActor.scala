package cn.qf.day06.demo04_actor.sample02_actor.way2_main2son.b_advanced

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

case class SonActor() extends Actor{
  override def act(): Unit = {
    loop {
      //收到与case分支匹配的消息就执行,否则阻塞
      react {//偏函数
        case "启动" => {
          println("启动中...")
          Thread.sleep(3000)
          println("启动完毕")
        }
        case "停止" => {
          println("停止中...")
          Thread.sleep(3000)
          println("停止完毕")
        }
      }
    }
  }
}
