package cn.qf.day04.sample01_trait.demo04_instantsort

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

trait ConsoleLogger extends Logger {
  println("ConsoleLogger类的实例被创建")
  /**
   * 处理日志信息
   * @param msg
   */
  override def log(msg:String) = {
    println("执行ConsoleLogger的方法")
    println(s"日志信息:$msg")
  }
}
