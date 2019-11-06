package cn.qf.util

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object BinarySearchUtil {
  def binarySearch(arr: Array[(String, String, String)], ip:Long): Int = {
    var start = 0
    var end = arr.length - 1
    while (start <= end) {
      //求中间值
      val middle = (start + end)/2
      //arr(middle)或去数据中的元组
      //元组存储从ip开始,ip结束,省份
      //因为需要判断时候在ip的范围之内,所以需要去除元组中的值
      //若这个条件满足就说明找到了ip
      if((ip >= arr(middle)._1.toLong) && (ip <= arr(middle)._2.toLong)) {
        return middle
      }
      if (ip < arr(middle)._1.toLong) {
        end = middle - 1
      }else {
        start = middle + 1
      }
    }
    -1
  }
}
