package cn.qf.day04.sample01_trait.demo02_asrichinterface

/**
 * Description：当做富接口使用的特质<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

trait Logger {
  def log(msg:String)

  /**
   * 日至框架:log4j:
   * @param msg
   */
  def info(msg: String) { log("INFO: " + msg) }
  def warn(msg: String) { log("WARN: " + msg) }
  def severe(msg: String) {log("SEVERE: " + msg)}
}
