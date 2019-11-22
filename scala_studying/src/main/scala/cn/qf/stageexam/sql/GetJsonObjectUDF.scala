package cn.qf.stageexam.sql

import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.spark.sql.api.java.UDF2

/**
  * 获取json串中某个字段值的UDF
  *
  * UDF2<String, String, String>
  * 前两个类型是值传进来的值的类型
  * 第一个类型代表json格式的字符串
  * 第二个代表要获取字段值的字段名称
  * 第三个类型代表返回json串里的某个字段的值
  */
class GetJsonObjectUDF extends UDF2[String, String, String] {
  @throws[Exception]
  override def call(json: String, field: String): String = {
    try {
      val jsonObject: JSONObject = JSON.parseObject(json)
      return jsonObject.getString(field)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
    null
  }
}