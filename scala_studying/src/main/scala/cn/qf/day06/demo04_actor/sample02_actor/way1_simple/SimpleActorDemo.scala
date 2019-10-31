package cn.qf.day06.demo04_actor.sample02_actor.way1_simple

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SimpleActorDemo {
  def main(args: Array[String]): Unit = {
    new MyActor().start
    new MyActor().start
  }
}
