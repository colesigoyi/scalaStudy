package cn.qf.day04.sample01_trait.demo04_instantsort

/**
 * Description：特质构造的顺序<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月24日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TraitInstanceSortDemo {
  def main(args: Array[String]): Unit = {
    new BankSavingAccount with ConsoleLogger with ShortLogger with TimeLogger
    /*
    实际演示的效果：
    Account类的实例被创建了哦。。。
    Logger类的实例被创建了哦。。。
    BankSavingAccount类的实例被创建了哦。。。
    ConsoleLogger类的实例被创建了哦。。。
    ShortLogger类的实例被创建了哦。。。
    TimeLogger类的实例被创建了哦。。。
     */
    new BankSavingAccount with TimeLogger
    /*
    Account类的实例被创建
    Logger类的实例被创建...
    BankSavingAccount类的实例被创建
    ConsoleLogger类的实例被创建
    TimeLogger类的实例被创建了
     */
  }
}
