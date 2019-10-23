package cn.qf.day02.sample02_collection.demo02_map

import scala.collection.mutable

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object MutableMapDemo {
  def main(args: Array[String]): Unit = {
    //容器
    val scores = mutable.Map("张无忌" -> 89,"张三丰" -> 99,"张翠山" -> 96,("东方不败",100))
    //增

    println("---------添加----------")
    scores("赵子龙") = 77
    scores.put("黄老邪",88)
    scores += (("黄蓉",100))
    println(s"可变的Map映射中的元素是:$scores")

    println("---------删除----------")
    //删
    val key = "张三丰"
    scores -= key
    scores.remove("张翠山")
    println(s"删除key=${key}后可变的Map映射中的元素是:$scores")

    println("---------修改----------")
    //改
    scores("张子龙") = 99
    println(s"修改后可变的Map映射中的元素是:$scores")
    //查

    println("---------查询----------")
    val zwjScores = scores("赵子龙")
    val zwjScores2 = scores.get("张无忌").get
    val zwjScores3 = scores.getOrElse("张翠山",0)
    println(s"查询Map映射中的元素是:$zwjScores")
    println(s"查询Map映射中的元素是:$zwjScores2")
    println(s"查询Map映射中的元素是:$zwjScores3")
    //遍历
    for (perKey <- scores.keys)
      println(s"$perKey:${scores.getOrElse(perKey,null)}")

    println("---------使用元组的方式遍历----------")
    //元组方式,map映射中的每个元素,本质就是对偶元组(元祖中有且仅有两个元素的元组)
    for ((key, value) <- scores)
      println(s"$key -> $value")
  }
}
