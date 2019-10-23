package cn.qf.day03.sample02_commonmethod

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CommonMethodDemo3 {
  def main(args: Array[String]): Unit = {
    //flatten:要求集合中的每个元素类型都是集合,效果:将集合中每个集合的元素中的元素取出,至于一个全新的集合存储起来
    val eles:Seq[Seq[String]] = Seq(Seq("jack","marry","tom"), Seq("杭州","武汉", "上海"))
    val result:Seq[String] = eles.flatten
    println(s"处理后:${result}")
    println("-----------------------------------")
    //flatMap = map + flatten
    //需求:将原集合中小写的单词转换成大写的形式,形式:WORLD, World
    val container = Seq("word","hello","abc")
    val lst = container.map(perEle => Seq(perEle.toUpperCase, perEle))//取出元素形成一个list
    lst.foreach(println)
    val newContainer:Seq[String] = lst.flatten
    println(s"传统做法:$newContainer")
    val newContainer2 = container.flatMap(perEle => List(perEle.toUpperCase, perEle))
    println(s"使用flaMap后:$newContainer2")
  }
}
