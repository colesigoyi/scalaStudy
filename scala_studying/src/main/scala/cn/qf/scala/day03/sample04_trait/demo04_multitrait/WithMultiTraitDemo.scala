package cn.qf.scala.day03.sample04_trait.demo04_multitrait

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WithMultiTraitDemo {
  def main(args: Array[String]): Unit = {
    /*
    从右往左执行
    执行TimeLogger
    执行ShortLogger
    执行ConsoleLogger
    日志信息:2019-10-23T09:58:3...
    */
    val account = new BankSavingAccount with ConsoleLogger with ShortLogger with TimeLogger
    println("------------")
    new BankSavingAccount with ConsoleLogger with ShortLogger with TimeLogger
    account.withDraw(2002)
    /*
    Account类的实例被创建
    Logger类的实例被创建...
    BankSavingAccount类的实例被创建
    ConsoleLogger类的实例被创建
    ShortLogger类的实例被创建
    TimeLogger类的实例被创建了
    ------------
    Account类的实例被创建
    Logger类的实例被创建...
    BankSavingAccount类的实例被创建
    ConsoleLogger类的实例被创建
    ShortLogger类的实例被创建
    TimeLogger类的实例被创建了
    执行TimeLogger
    执行ShortLogger
    执行ConsoleLogger
    日志信息:2019-10-24T12:37:0...
         */
  }
}
