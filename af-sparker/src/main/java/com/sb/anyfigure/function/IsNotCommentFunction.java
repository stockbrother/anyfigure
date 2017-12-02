package com.sb.anyfigure.function;

import org.apache.spark.api.java.function.Function;

public class IsNotCommentFunction implements Function<String, Boolean> {

	public IsNotCommentFunction() {
	}

	@Override
	public Boolean call(String v1) throws Exception {
		if (v1.startsWith("//")) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

}
