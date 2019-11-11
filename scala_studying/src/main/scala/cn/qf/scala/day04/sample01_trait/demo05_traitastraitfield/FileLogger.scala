package cn.qf.scala.day04.sample01_trait.demo05_traitastraitfield

import java.io.PrintWriter

/**
 * Description：初始化特质中的字段<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
trait FileLogger extends Logger {
  val fileName:String
  lazy val pw:PrintWriter = new PrintWriter(fileName)

  /**
   * 显示日志信息
   *
   * @param msg
   */
  override def log(msg: String): Unit = {
    pw.println(msg)
    pw.flush()
    pw.close()
  }
}
