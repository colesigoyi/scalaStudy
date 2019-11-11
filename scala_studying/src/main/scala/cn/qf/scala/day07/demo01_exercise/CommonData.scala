package cn.qf.scala.day07.demo01_exercise

/**
 * Description：共通消息封装类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月29日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

/**
 * 下述多例样例类中封装了任务提交的信息
 * @param fileName
 */
case class SubmitTask(fileName:String)

/**
 * 下述多例样例类中封装了当前线程对文件计算后的结果
 * @param result
 */
case class ReturnResult(result: Map[String, Int])