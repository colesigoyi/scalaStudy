package cn.qf.day01.sample07_circle.demo1_for

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月21日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ForCircleDemo {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {//隐式转换:底层自动将scala中的类型:Int隐式转换成了RichInt
      println(i)
    }

    println("------------------------------")

    val names = Array("小明","小红","小兰")
    //一般形式
    for (n <- names){
      println(n)
    }

    println("------------------------------")

//    for (index <- 0 until names.length){
//      println(names(index))
//    }
    //通过索引来遍历
    for (index <- names.indices){
      println(names(index))
    }

    println("------------------------------")
    //遍历字符串
    val str = "学习scala......"
    //集合形式遍历
    for (perChar <- str) {
      println(perChar)
    }
    println("------------------------------")
    //索引方式遍历
    for (perChar <- str.indices) {
      println(str(perChar))
    }
    println("------------------------------")
    for (i <- 1 to 3; j <- 1 to 3; if i != j){
      println((10*i + j) + " ")
    }
    println("------------九九乘法表-----------")
    for (j <- 1 to 9; i <- 1 to 9; if i <= j){
      print(s"$i x $j = " + (i * j) + "   ")
      if (j==i) println()
    }
    println("-------------for推导式-----------------")
    val newNums = for(i <- 1 to 3) yield 2 * i  //yield将源集合中的每个元素取出来
    println(newNums) //Vector(2, 4, 6)

    println("--------------数组中常用的方法----------------")
    val nums = Array(1,2,3)
    //_ : 神奇的下划线，在scala中用法很多，相当灵活，此处用作通配符，代指数组中的每个元素
    //map:依次取出数组中的每个元素,进行运算,将结果放到新的数组中
    println(s"新的数组中的元素是:${nums.map(_*2).toBuffer}")
    //filter:依次取出数组中的每个元素,使用指定的boolean型的表达式进行判断,将满足条件的元素放到全新的集合中
    println(s"新的数组中的所有奇数是:${nums.filter(_%2!=0).toBuffer}")
  }
}
