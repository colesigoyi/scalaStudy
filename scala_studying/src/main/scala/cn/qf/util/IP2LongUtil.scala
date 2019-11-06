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
object IP2LongUtil {
  def ip2Long(ipStr:String) = {
    val arr = ipStr.split("\\.")
    var ipNum = 0L
    for (perEle <- arr) {
      //按位或 只要对应的两个二进制有一个为1时,结果就是1 << 左位移
      ipNum = perEle.toLong | ipNum << 8L
    }
  ipNum
  }
}
