package cn.qf.day03.sample01_collection.demo03_exercise

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月23日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object CollectionExerciseDemo {
  def main(args: Array[String]): Unit = {
    //需求:将集合中所有的单词出现的总次数进行统计,并根据次数降序输出

    //前提:准备容器,其中存放一些单词
    val words = Seq("hello hello are  hi hi hi you  you", "hi  fine good are are  good",
      "hello", "hi  fine good are are  good","hello", "hi  fine good are are  good","hello")
    println("-------------拆分版--------------------")
    //步骤
    //1.将集合中所有的单词取出,至于一个全新的集合中(flaMap)
    val perWordList:Seq[String] = words.flatMap(_.split("\\s+"))
    println(perWordList)//List(hello, hello, are, hi, hi, hi, you, you, hi, fine,
    // good, are, are, good, hello, hi, fine, good, are, are, good, hello, hi, fine,
    // good, are, are, good, hello)


    //2.将集合中每个元素变形为元组(map)
    val tupleList:Seq[(String,Int)] = perWordList.map((_,1))
    println(tupleList)//List((hello,1), (hello,1), (are,1), (hi,1), (hi,1), (hi,1),
    // (you,1), (you,1), (hi,1), (fine,1), (good,1), (are,1), (are,1), (good,1),
    // (hello,1), (hi,1), (fine,1), (good,1), (are,1), (are,1), (good,1), (hello,1),
    // (hi,1), (fine,1), (good,1), (are,1), (are,1), (good,1), (hello,1))


    //3.根据集合中每个元素(元组)的key进行分组(groupBy)
    val mapResult:Map[String, Seq[(String, Int)]] = tupleList.groupBy(_._1)
    println(mapResult)//Map(are -> List((are,1), (are,1), (are,1), (are,1), (are,1),
    // (are,1), (are,1)), good -> List((good,1), (good,1), (good,1), (good,1),
    // (good,1), (good,1)), you -> List((you,1), (you,1)), hi -> List((hi,1), (hi,1),
    // (hi,1), (hi,1), (hi,1), (hi,1)), fine -> List((fine,1), (fine,1), (fine,1)),
    // hello -> List((hello,1), (hello,1), (hello,1), (hello,1), (hello,1)))



    //4.分析map集合中每个元素的值(List集合),集合的长度即是单词出现的次数(mapValue)
    val newmapResult:Map[String, Int] = mapResult.mapValues(_.size)
    println(newmapResult)//Map(are -> 7, good -> 6, you -> 2, hi -> 6, fine -> 3, hello -> 5)

    //5.将map集合转化为List集合(toList)
    val lst:List[(String, Int)] = newmapResult.toList
    println(lst)//List((are,7), (good,6), (you,2), (hi,6), (fine,3), (hello,5))

    //6.对List中每个元素进行降序排列
    val sortedList:List[(String, Int)] = lst.sortWith(_._2 > _._2)
    //7.将排序后的结果输出
    sortedList.foreach(perEle => println(perEle._1 + "\t" + perEle._2))
          /*are	7
          good	6
          hi	6
          hello	5
          fine	3
          you	2
          are	7
          good	6
          hi	6
          hello	5
          fine	3
          you	2
          */
    words.flatMap(_.split("\\s+"))
      .map((_, 1))
      .groupBy(_._1)
      .mapValues(_.size)
      .toList
      .sortWith(_._2 > _._2)
      .foreach(perEle => println(perEle._1 + "\t" + perEle._2))
  }
}
