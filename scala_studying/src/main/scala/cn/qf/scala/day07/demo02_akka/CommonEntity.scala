package cn.qf.scala.day07.demo02_akka

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonEntity {
  /**
   * 超时时间设定
   */
  val TIME_OUT_SECONDS = 5
}

//_______________________  Common  __________________________
//_______________________   Worker  __________________________

/**
 * 注册信息样例类
 *
 * @param id
 * @param host
 * @param memory
 * @param coreNums
 */
case class RegisterInfo(id: String, host: String, memory: Int, coreNums: Int)


/**
 * 注册后的信息样例类
 *
 * @param id
 */
case class RegisteredInfo(id: String)


/**
 * 心跳包样例类
 *
 * @param id
 */
case class HeatBeat(id: String)

//_______________________   Master  __________________________
/**
 * 超时样例类
 */
case object TimeOut

/**
 * Worker进程信息的样例类
 *
 * @param id
 * @param host
 * @param memory
 * @param coreNums
 * @param lastSendHearBeatTime
 */
case class WorkerInfo(id: String, host: String, memory: Int, coreNums: Int, var lastSendHearBeatTime: Long)


