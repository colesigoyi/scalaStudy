package cn.qf.scala.day04.sample01_trait.demo05_traitastraitfield

/**
 * Description：初始化特质中的字段<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */

class BankSavingAccount extends Account with Logger {

  def withDraw(amount: Double) = {
    if (amount > balance)
      log(s"警告!您银行存款的余额是:$balance 元,你现在要取款的金额是:$amount")
    else {
      balance -= amount
      log(s"银行存款剩余:${balance}")
    }
  }
}
