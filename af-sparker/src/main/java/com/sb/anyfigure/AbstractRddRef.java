package com.sb.anyfigure;

import org.apache.spark.api.java.AbstractJavaRDDLike;

public class AbstractRddRef<K, T extends AbstractJavaRDDLike<K, T>> {
	protected AbstractJavaRDDLike<K, T> rddLike;
}
