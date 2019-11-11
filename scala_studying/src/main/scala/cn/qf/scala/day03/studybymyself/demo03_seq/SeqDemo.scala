package cn.qf.scala.day03.studybymyself.demo03_seq

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object SeqDemo extends App {
  val nums = Seq(11,22,32,2,53,6,12,16,43,24) //序列
  println(s"nums序列最大值:${nums.max}")
  println(s"nums序列最小值:${nums.min}")
  println("-------------------------------------------------------------")

  case class Book(title: String, pages: Int)
  val books = Seq(Book("Future of Scala developers", 85),
    Book("Parallel algorithms", 240),
    Book("Object Oriented Programming", 130),
    Book("Mobile Development", 495))

  println(s"页数最多的书籍是:<<${books.maxBy(book => book.pages).title}>>")
  println(s"页数最少的书籍是:<<${books.minBy(book => book.pages).title}>>")
  val bookList = books.filter(book => book.pages >=120)
  print(s"页数大于120页的书籍是:")
  for (book <- bookList)
    print(s"<<${book.title}>>,")

  println("-------------------------------------------------------------")

  val nums2 = Seq(1,2,3,4,5,6,7,8,9,10)
  val nums3 = nums2.filter(n => n % 2 == 0)
  println(s"$nums3")

  println("-------------------------------------------------------------")

  val abcd = Seq('a', 'b', 'c', 'd')
  val efgj = Seq('e', 'f', 'g', 'h')
  val ijkl = Seq('i', 'j', 'k', 'l')
  val mnop = Seq('m', 'n', 'o', 'p')
  val qrst = Seq('q', 'r', 's', 't')
  val uvwx = Seq('u', 'v', 'w', 'x')
  val yz = Seq('y', 'z')
  val alphabet = Seq(abcd, efgj, ijkl, mnop, qrst, uvwx, yz)
  println(alphabet.flatten.reverse)
  println(alphabet.flatten)

  println("-------------------------------------------------------------")

  val number1 = Seq(1,2,3,4,5)
  val number2 = Seq(2,4,6,7,8)

  println(s"number1的集合是:${number1}")
  println(s"number2的集合是:${number2}")
  println(s"number1中有的,number2中没有的集合是:${number1.diff(number2)}")
  println(s"number1和number2的都有的集合是:${number1.intersect(number2)}")
  println(s"number1中没有的,number2中有的集合是:${number2.diff(number1)}")
  println(s"number1与number2共同的集合是:${number1.union(number2).distinct}")

  println("-------------------------------------------------------------")
  //map 函数的逻辑是遍历集合中的元素并对每个元素调用函数。
  val number3 = Seq(1,2,3,4,5,6)
  println(number3.map(n => n * 2))
  val chars = Seq('a','b','c','d')
  println(chars.map(ch => ch.toUpper))

  println("-------------------------------------------------------------")

  println(chars.flatMap(ch => List(ch.toUpper, ch)))  //flatMap

  println("-------------------------------------------------------------")
  //对整个集合进行条件检查
  val number4 = Seq(3, 7, 2, 9, 6, 5, 1, 4, 2)
  println(number4.forall(n => n < 10))
  println(number4.forall(n => n > 5))

  println("-------------------------------------------------------------")

  val number5 = Seq(3, 7, 2, 9, 6, 5, 1, 4, 2)
  println(number5.partition(n => n % 2 == 0))

  println("-------------------------------------------------------------")
  //折叠
  /*
  List中的fold方法需要输入两个参数：初始值以及一个函数。输入的函数也需要输入两个参数：累加值和当前item的索引。那么上面的代码片段发生了什么事？
　　代码开始运行的时候，初始值0作为第一个参数传进到fold函数中，list中的第一个item作为第二个参数传进fold函数中。
　　1、fold函数开始对传进的两个参数进行计算，在本例中，仅仅是做加法计算，然后返回计算的值；
　　2、Fold函数然后将上一步返回的值作为输入函数的第一个参数，并且把list中的下一个item作为第二个参数传进继续计算，同样返回计算的值；
　　3、第2步将重复计算，直到list中的所有元素都被遍历之后，返回最后的计算值，整个过程结束；
　　4、这虽然是一个简单的例子，让我们来看看一些比较有用的东西。早在后面将会介绍foldLeft函数，并解释它和
      fold之间的区别，目前，你只需要想象foldLeft函数和fold函数运行过程一样。
   */
  val numbers = Seq(1, 2, 3, 4, 5)  //(((1+2)+3)+4)+5
  println(numbers.foldLeft(0)((res, n) => res + n))
  val words = Seq("apple", "dog", "table")
  println(words.foldLeft(0)((resultLength, word) => resultLength + word.length))
  /*
  fold, foldLeft, and foldRight之间的区别
　　主要的区别是fold函数操作遍历问题集合的顺序。
     foldLeft是从左开始计算，然后往右遍历。
     foldRight是从右开始算，然后往左遍历。而fold遍历的顺序没有特殊的次序。
   */
}

