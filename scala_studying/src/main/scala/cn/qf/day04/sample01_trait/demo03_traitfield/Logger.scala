package cn.qf.day04.sample01_trait.demo03_traitfield

/**
 * Description：特质中的普通字段和抽象字段<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

trait Logger {
  /**
   * 普通常量属性
   */
  val MAX_LEN = 18
  /**
   * 抽象常量属性
   */
  val INFO:String
  /**
   * 普通变量属性
   */
  var MAX_LEN_VAR = 18
  /**
   * 抽象变量属性
   */
  var INFO_VAR:String
}
