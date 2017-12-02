package com.sb.anyfigure.function;

import java.time.MonthDay;

import org.apache.spark.api.java.function.Function;

import com.sb.anyfigure.tuple.DateStringDecimalTuple;

public class IsMonthDayAttributeFunction implements
		Function<DateStringDecimalTuple, Boolean> {

	private MonthDay month;
	private String name;

	public IsMonthDayAttributeFunction(String name, MonthDay month) {
		this.name = name;
		this.month = month;
	}

	@Override
	public Boolean call(DateStringDecimalTuple v1) throws Exception {

		if (v1.isAttribute(this.name) && v1.isMonthDay(this.month)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

}
