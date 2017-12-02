package com.sb.anyfigure;

import org.apache.spark.api.java.JavaRDD;

public interface Calculator<T> {

	public JavaRDD<T> calculate(CalculateContext cs);

}
