package com;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

import com.google.common.collect.Lists;

public class StreamTest {
	public static void main(String[] args) throws Exception {

		// StreamingExamples.setStreamingLogLevels();
		Logger.getRootLogger().setLevel(Level.WARN);
		
		SparkConf sparkConf = new SparkConf().setAppName("JavaQueueStream")
				.setMaster("local[*]");

		// Create the context
		JavaStreamingContext ssc = new JavaStreamingContext(sparkConf,
				new Duration(10));

		// Create the queue through which RDDs can be pushed to
		// a QueueInputDStream
		Queue<JavaRDD<Integer>> rddQueue = new LinkedList<JavaRDD<Integer>>();

		// Create and push some RDDs into the queue
		List<Integer> list = Lists.newArrayList();
		for (int i = 0; i < 1000; i++) {
			list.add(i);
		}

		for (int i = 0; i < 1; i++) {
			rddQueue.add(ssc.sparkContext().parallelize(list));
		}

		// Create the QueueInputDStream and use it do some processing
		JavaDStream<Integer> inputStream = ssc.queueStream(rddQueue);
		JavaPairDStream<Integer, Integer> mappedStream = inputStream
				.mapToPair(new PairFunction<Integer, Integer, Integer>() {
					@Override
					public Tuple2<Integer, Integer> call(Integer i) {
						return new Tuple2<Integer, Integer>(i % 10, 1);
					}
				});
		JavaPairDStream<Integer, Integer> reducedStream = mappedStream
				.reduceByKey(new Function2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer i1, Integer i2) {
						return i1 + i2;
					}
				});

		reducedStream.print();
		ssc.start();
		ssc.awaitTermination();
	}
}
