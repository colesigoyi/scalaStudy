package cn.qf.scala.day02.sample02_collection.demo03_tuple

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object TupleDemo {
  def main(args: Array[String]): Unit = {
    val tuple,(name, age, address) = ("jack", 17, "杭州")
    println(s"元组中的元素是: $tuple")
    println("-------------元祖中的元素--------------")
    println(s"元组中的第一个元素是:${tuple._1}")
    println(s"元组中的第二个元素是:${tuple._2}")
    println(s"元组中的第三个元素是:${tuple._3}")
    println(s"名字:${name}\n年龄:${age}\n地址:${address}")
    println("----------遍历-----------")
    tuple.productIterator.foreach(println)
    println("-------------------------")
    for (perEle <- tuple.productIterator)
      println(perEle)
    println("-------------------------")
    //元组的本质
    val tupleTruth = new Tuple3("jack", 17, "杭州")
    println(s"元组中的第一个元素是:${tuple._1}")
    println(s"元组中的第二个元素是:${tuple._2}")
    println(s"元组中的第三个元素是:${tuple._3}")

    println("----------拉链操作------------")
    //数组进行拉练操作,操作后的新的数组中的每个元素类型是元组
    val names = Array("张无忌","陆小凤")
    val ages = Array(19, 18)
    val stus:Array[(String, Int)] = names.zip(ages)
    println(s"拉练操作之后的结果是:${stus.toBuffer}")

    println("----------特殊元组------------")
    val spacialTuple = ("jack",17,("中国","浙江","九堡"))
    val addressInfo = s"${spacialTuple._3._1}${spacialTuple._3._2}${spacialTuple._3._3}"
    println(addressInfo)
  }
}
