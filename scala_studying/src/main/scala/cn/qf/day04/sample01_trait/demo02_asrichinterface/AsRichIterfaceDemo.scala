package cn.qf.day04.sample01_trait.demo02_asrichinterface

/**
 * Description：当做富接口使用的特质<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

object AsRichIterfaceDemo {
  def main(args: Array[String]): Unit = {
    val account = new BankSavingAccount
    account.withDraw(3400)
  }
}
