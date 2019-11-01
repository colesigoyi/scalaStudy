package cn.qf.day09.demo02_wordcount;/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月31日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Row;
import scala.Int;
import scala.Tuple2;

import java.util.Arrays;

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-10-31 11:37
 * @ desc:  
 **/

public class WordCountDemo2 {
    public static void main(String[] args) {
        //前提:
        //1.连接非法字符
        if(args.length != 2) {
            System.out.println("警告");
            System.exit(-1);
        }

        //2.动态获取得待计算的源,以及计算后结果落地的目录
        String inputPath = args[0].trim();
        String outputPath = args[1].trim();
        //3.步骤
        //1.SparkConf
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName(WordCountDemo2.class.getSimpleName());

        //SparkContext
        JavaSparkContext jsc = new JavaSparkContext(conf);
        //textFile
        //fileMap
        //map
        //reduceByKey
        //排序
        //saveAsTextFile

        jsc.textFile(inputPath)
                .flatMap(perEle -> Arrays.asList(perEle.split("\\s+"))
                        .iterator())
                .mapToPair(perEle -> new Tuple2<>(perEle, 1))
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey(false,1)
                .mapToPair(Tuple2::swap)
                .foreach(result -> System.out.println(result));
                //.foreach((VoidFunction<Tuple2<String, Integer>>) System.out::println);
                //.saveAsTextFile(outputPath);

        //资源释放
        jsc.stop();
    }
}