package cn.qf.day03.sample04_trait.demo03_withtraitobject

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object WithDetailImplementsTrait {
  def main(args: Array[String]): Unit = {
    val account = new BankSavingAccount with ConsoleLogger
    account.withDraw(500)

  }
}
