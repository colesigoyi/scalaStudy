package cn.qf.day13.demo04_sort.sample02_ordered

import cn.qf.util.SparkUtil

/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月05日  
 *
 * @author 陶雪峰
 * @version : 1.0
 */
object OrderedDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession(OrderedDemo.getClass.getName,"local[*]")
    val sc = spark.sparkContext

    sc.parallelize(Seq(Girl("marry", 80.9, 18, 174.6, 50.3),
      Girl("lucycat",98.9, 20, 179.1, 48.1),
      Girl("Angle",80.9, 21, 172.1, 56.8),
      Girl("kate",80.9, 21, 172.1, 56.1),
      Girl("lilydo", 80.9, 21, 176.2, 51.5)))
      .sortBy(G => G,ascending = true, 1)
      .foreach(println)
      /*
      Girl(lucycat,98.9,20,179.1,48.1)
      Girl(marry,80.9,18,174.6,50.3)
      Girl(lilydo,80.9,21,176.2,51.5)
      Girl(kate,80.9,21,172.1,56.1)
      Girl(Angle,80.9,21,172.1,56.8)
       */
    /*
    sortBy第二个参数:
        true->根据定制的规则进行排列(降序)
        false→升序，恰好与规则相反(升序)
    注意点:待比较的数值是doub1e型，整数相同，小数不同,不能很精准地比较.需要将小数乘以相应的倍数
    (若是有两个小数点，都乘以100倍)转换成int,就可以比较了。
    最终的结果除以相应的倍数即可。
    sortBy方法的第二个参数列表中的第一个参数要求是Ordering类型,
    此处传入一个ordered[Girl]，为什么可以?
    在ordering. scala源程序中，有隐式方法:
      implicit def ordered[A <% Comparab7e[A]]: ordering[A] = new ordering[A] {
      def compare(x: A，y: A): Int = X compareTo y
        }
      }
     */
    spark.stop()
  }
}
