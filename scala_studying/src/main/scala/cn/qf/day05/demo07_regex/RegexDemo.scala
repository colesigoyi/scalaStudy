package cn.qf.day05.demo07_regex

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object RegexDemo {
  def main(args: Array[String]): Unit = {
    //准备正则表达式实例
//    val regex = "\\d+".r
    //三双引号将正则表达式括起来,不用转义字符即可识别
    val regex = """\d+""".r
    //准备一条待分析的日志信息
    val msg = "78  * good ,#98* :hehe, 99"

    //		findAllIn -> 从参数指定的字符串中剥离出于正则表达式相匹配的子串,返回的是迭代器
    regex.findAllIn(msg).foreach(println)

    //		findFirstIn -> 从参数指定的字符串中剥离出第一个与正则表达式相匹配的子串,返回的是Option[T]
    println(s"满足条件的第一个子串信息是:${regex.findFirstIn(msg).get}")

    //		findPrefixOf -> 等价于findFirstIn
    println(s"满足条件的第一个子串信息是:${regex.findPrefixOf(msg).get}")

    //		replaceFirstIn -> 使用新的字符串替换与正则表达式吻合的第一个子串,返回替换后的新字符串
    println(s"新的字符串是是:${regex.replaceFirstIn(msg, "->")}")

    //		replaceAllIn -> 使用新的字符串替换与正则表达式吻合的所有子串,返回替换后的新字符串
    println(s"新的字符串是是:${regex.replaceAllIn(msg, "☆")}")
  }
}
