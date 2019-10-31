package cn.qf.day09.demo1_wordcount;/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年10月31日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-10-31 10:54
 * @ desc:  
 **/

public class WordCountDemo {
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
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName(WordCountDemo.class.getSimpleName());

        //SparkContext
        JavaSparkContext jsc = new JavaSparkContext(conf);
        //textFile
        JavaRDD<String> fileRDD = jsc.textFile(inputPath);
        //fileMap
        JavaRDD<String> perWordRDD = fileRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split("\\s+")).iterator();
            }
        });
        //map
        JavaRDD<String> filterAfterRDD = perWordRDD.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String v1) throws Exception {
                return !"".equals(perWordRDD);
            }
        });
        JavaPairRDD<String, Integer> tupleRDD = filterAfterRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String perWord) throws Exception {
                return new Tuple2<>(perWord, 1);
            }
        });
        //reduceByKey
        JavaPairRDD<String, Integer> reduceAfterRDD = tupleRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });


        //排序
        JavaPairRDD<Integer, String> swapRDD = reduceAfterRDD.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> oldTuple) throws Exception {
                return oldTuple.swap();
            }
        });

        JavaPairRDD<String, Integer> resultTuple =  swapRDD.sortByKey(false, 1).mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> tmpTuple) throws Exception {
                return tmpTuple.swap();
            }
        });
        resultTuple.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> resultTuple2) throws Exception {
                System.out.println(resultTuple2._1 + "\t\t" + resultTuple2._2);
            }
        });
        //saveAsTextFile
        resultTuple.saveAsTextFile(outputPath);
        //资源释放
        jsc.stop();

    }
}
