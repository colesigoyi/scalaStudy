package cn.qf.scala.day05.demo08_highlevelfunction

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月25日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object ReturnFunctionType {
  def main(args: Array[String]): Unit = {
    val getUrl = getFullURL(true, "www.baidu.com")
    val finalURL = getUrl("user", "id=4")
    println(s"最终的URL信息是:$finalURL")
  }

  /**
   * 获得一个完整的URL信息
   * @param isSafe  是否是安全的http协议
   * @param domain  域名
   * @return  返回值类型是函数类型
   */
  def getFullURL(isSafe:Boolean, domain:String):(String,String) => String = {
    val protocal = if (isSafe) "https://" else "http://"
    (modulename:String, condition:String) => s"$protocal$domain/$modulename?$condition"
  }
}
