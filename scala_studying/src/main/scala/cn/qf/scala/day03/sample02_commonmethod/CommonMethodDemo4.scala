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
object CommonMethodDemo4 {
  def main(args: Array[String]): Unit = {
    val nums1 = Seq(1,2,3,4)
    val nums2 = Seq(3,4,5,6)

    //交集
    val joinResult = nums1.intersect(nums2)
    println(s"${nums1}与${nums2}的交集是:${joinResult}")
    //差集
    val subtractResult = nums1.diff(nums2)
    println(s"${nums1}与${nums2}的差集是:${subtractResult}")
    //并集
    val unionResult = nums1.union(nums2)
    println(s"${nums1}与${nums2}的并集是:${unionResult},去重的并集是:${unionResult.distinct}")

    val nums = Seq(99,88,78,59,97,34,78)
    val result = nums.forall(_ >= 60)
    println(result)

    val scores = Seq(99,88,78,59,97,34,78,23)
    val t,(team1, team2) = scores.partition(_ >= 60)
    println(s"及格的分数:$team1")
    println(s"及格的分数:${t._1}")
    println(s"不及格的分数:$team2")
    println(s"不及格的分数:${t._2}")
    //fold
    val num = Seq(1,2,3,4)
//    val sum = num.fold(0)((sum, n) => sum * n)
//    val sum = num.fold(0)(_ + _)//累加 sum
//    val sum = num.fold(1)(_ * _)//累乘  product
    val sum = num.foldLeft(0)(_ - _)//累减
    val sum2 = num.foldRight(5)(_ - _)  /*
    Seq(1,2,3,4) -> Seq(4,3,2,1)
    调用op(y,x)
    4-5=-1
    3-(-1)=4
    2-4=-2
    1-(-2)=3
    等价于4-3-2-1+5
    */
    println(s"sum=${sum}")
    println(s"sum=${sum2}")
    //reduce  reduceLeft  reduceRight
    println(s"reduce方法:${num.reduce(_ + _)}")
  }
}
