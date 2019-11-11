package cn.qf.scala.day04.sample01_trait.demo03_traitfield

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class ConsoleLogger extends Logger {
  /**
   * 抽象常量属性
   */
  override val INFO: String = "普通信息..."
  /**
   * 抽象变量属性
   */
  override var INFO_VAR: String = "爬虫NODE宕机了..."
  /**
   * 普通常量属性:重写普通的常量属性,必须加override关键字
   */
  override val MAX_LEN = 18
  /**
   * 普通变量属性:不能被重写
   */
  //override var MAX_LEN_VAR = 18

}
