package cn.qf.day02.sample02_collection.demo04_list

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月22日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ImmutableListDemo {
  def main(args: Array[String]): Unit = {

    val nums = List(1,2,3)

    println("-------------- :: 在列表前添加元素----------------")
    //:: 在列表前添加元素
    val newNums = -1 :: nums
    println(s"旧列表:$nums")
    println(s"新列表:$newNums")
    println("------------------------------------------------")
    val newNums2 = nums.::(-1)
    println(s"旧列表:$nums")
    println(s"新列表:$newNums2")

    println("-------------- +: 在列表前添加元素----------------")
    //+:
    val newNums3 = -1 +: nums
    println(s"旧列表:$nums")
    println(s"新列表:$newNums3")
    println("------------------------------------------------")
    val newNums4 = nums.+:(-1)
    println(s"旧列表:$nums")
    println(s"新列表:$newNums4")

    println("-------------- ++ 在列表前添加list----------------")
    //++
    val lst = List(4,5,6)
    val newNums5 = lst ++ nums
    println(s"旧列表:$nums")
    println(s"新列表:$newNums5")
    println("--------------- .++ 在列表后添加list ----------------")
    val newNums6 = nums.++(lst)
    println(s"旧列表:$nums")
    println(s"新列表:$newNums6")

    println("-------------- ::: 在列表前添加list----------------")
    //::: 将两个列表合并成新的列表,哪个列表在前,就在新列表的前面
    val lst2 = List(7,8,9)
    val newNums7 = lst2 ::: nums
    println(s"旧列表:$nums")
    println(s"新列表:$newNums7")
    println("-------------- ::: 在列表后添加list----------------")
    val newNums8 = nums ::: lst2
    println(s"旧列表:$nums")
    println(s"新列表:$newNums8")
    println("--------------- .::: 在列表前添加list ----------------")
    val newNums9 = nums.:::(lst2)
    println(s"旧列表:$nums")
    println(s"新列表:$newNums9")
  }
}
