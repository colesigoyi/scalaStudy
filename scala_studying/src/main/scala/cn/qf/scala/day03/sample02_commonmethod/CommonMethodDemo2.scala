package cn.qf.scala.day03.sample02_commonmethod

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonMethodDemo2 {
  def main(args: Array[String]): Unit = {
    val books = Seq(Books("Mysql从删库到跑路", 85),
      Books("Java从入门到出家", 240),
      Books("大数据的坑", 130),
      Books("Scala从入门到放弃", 495))

    //    val maxPageBook = books.maxBy(book => book.pages)
    val maxPageBook = books.maxBy(_.pages)
    println(s"页码最多的书是:${maxPageBook.title},页码数是:${maxPageBook.pages}")
    //    val minPageBook = books.minBy(book => book.pages)
    val minPageBook = books.minBy(_.pages)
    println(s"页码最少的书是:${minPageBook.title},页码数是:${minPageBook.pages}")

    val bookList = books.filter(_.pages >= 120)
    for (book <- bookList)
      println(s"页码大于120的书是:${book.title},页码数是:${book.pages}")

  }
}
case class Books(title:String, pages:Int)