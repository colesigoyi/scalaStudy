package cn.qf.scala.day04.sample01_trait.demo04_instantsort

/**
 * Description：特质构造的顺序<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
trait ShortLogger extends ConsoleLogger {
  println("ShortLogger类的实例被创建")

  val MAX_LEN = 18

  override def log(msg: String): Unit = {
    println("执行ShortLogger方法")
    super.log(
      if (msg.length <= MAX_LEN) msg else msg.substring(0, MAX_LEN) + "..."
    )
  }
}
