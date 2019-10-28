package cn.qf.day06.demo03_generic.sample02_nibian

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月28日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object NiBianDemo {
  def main(args: Array[String]): Unit = {
    //情形1:没有使用逆变的情形,同类型传递
    val catPrinter = new CatPrinter
    showCatInfo(Cat("小花猫"), catPrinter)
    println("------------------------------")
    //情形2:逆变
    val animalPrinter = new AnimalPrinter
    showCatInfo(Cat("大花猫"), animalPrinter)
    //p:Printer[Cat] = new AnimalPrinter,而AnimalPrinter extends Printer[Animal]
  }
  def showCatInfo(cat:Cat, p:Printer[Cat]) = p.show(cat)
}
