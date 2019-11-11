package cn.qf.scala.day05.demo07_regex

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object RegexGroupDemo {
  def main(args: Array[String]): Unit = {
    //正则表达式组
//    val regex = """([1-9]+) ([a-z]+)""".r
    val regex = """(\d+) (\w+)""".r
    //日志信息
    val msg = "123 abc"
    //方式1:迭代器
    var nums: Int = 0
    var words: String = ""
    for (regex(tempNum,tempWord) <- regex.findAllIn(msg)) {
      nums = tempNum.toInt
      words = tempWord
    }
    println(s"num = $nums ,word = $words")

    //方式2:模式匹配
    msg match {
      case regex(num, word) => println(s"num = $num ,word = $word")
      case _ => println("no match")
    }
  }
}
