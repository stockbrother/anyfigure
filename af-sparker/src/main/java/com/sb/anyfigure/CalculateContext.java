package com.sb.anyfigure;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;

public class CalculateContext {
	public JavaSparkContext sparkContext;
	public JavaRDD<OrgDateGroupStringDecimalTuple> ogdsdRdd;

	public CalculateContext(JavaSparkContext jsc,
			JavaRDD<OrgDateGroupStringDecimalTuple> rdd) {
		this.sparkContext = jsc;
		this.ogdsdRdd = rdd;
	}
}
