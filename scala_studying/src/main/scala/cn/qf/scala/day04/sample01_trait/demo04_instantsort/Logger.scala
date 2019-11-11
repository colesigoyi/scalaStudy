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
trait Logger {
  println("Logger类的实例被创建...")

  /**
   * 显示日志信息
   * @param msg
   */
  def log(msg:String) = {
    println("Logger中的方法log执行了...")
  }
}
