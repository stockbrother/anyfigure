package com.sb.anyfigure.function;

import org.apache.spark.api.java.function.Function;

public class ReportDateFunction implements Function<String, Boolean> {

	@Override
	public Boolean call(String v1) throws Exception {
		if (v1.startsWith("报告日期")) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

}
