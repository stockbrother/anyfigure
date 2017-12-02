package com.sb.anyfigure;

import java.util.List;
import java.util.function.Consumer;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class JavaRddWraper<T> {

	public static <K, V> void dump(JavaPairRDD<K, V> rdd) {
		rdd.foreach(new VoidFunction<Tuple2<K, V>>() {

			@Override
			public void call(Tuple2<K, V> t) throws Exception {
				System.out.println(t);
			}
		});
	}

	public static <T> void dump(JavaRDD<T> rdd) {
		List<T> l = rdd.collect();
		l.forEach(new Consumer<T>() {

			@Override
			public void accept(T t) {
				System.out.println(t);//
			}
		});

	}

}
