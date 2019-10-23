package cn.qf.day03.sample04_trait.demo04_multitrait

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
trait ShortLogger extends ConsoleLogger {

  val MAX_LEN = 18

  override def log(msg: String): Unit = {
    println("执行ShortLogger")
    super.log(
      if (msg.length <= MAX_LEN) msg else msg.substring(0, MAX_LEN) + "..."
    )
  }
}
