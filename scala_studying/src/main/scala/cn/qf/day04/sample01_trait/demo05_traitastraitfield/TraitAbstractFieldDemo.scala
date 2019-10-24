package cn.qf.day04.sample01_trait.demo05_traitastraitfield

/**
 * Description：初始化特质中的字段<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TraitAbstractFieldDemo {
  def main(args: Array[String]): Unit = {
    val instance = new BankSavingAccount with FileLogger {
      override val fileName: String = "log/info.txt"
    }
    instance.withDraw(800)
  }
}
