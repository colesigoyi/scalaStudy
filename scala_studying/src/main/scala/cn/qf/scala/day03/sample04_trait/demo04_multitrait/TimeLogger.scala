package cn.qf.scala.day03.sample04_trait.demo04_multitrait

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
trait TimeLogger extends ConsoleLogger {
  println("TimeLogger类的实例被创建了")
  override def log(msg: String): Unit = {
    println("执行TimeLogger")
    super.log(
      s"${java.time.Instant.now()} $msg"
    )

  }
}
