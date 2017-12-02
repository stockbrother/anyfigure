package com.sb.anyfigure.function;

import java.time.MonthDay;

import org.apache.spark.api.java.function.Function;

import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;

public class IsMonthDayFunction implements
		Function<OrgDateGroupStringDecimalTuple, Boolean> {

	private MonthDay month;

	public IsMonthDayFunction(MonthDay month) {
		this.month = month;
	}

	@Override
	public Boolean call(OrgDateGroupStringDecimalTuple v1) throws Exception {

		if (v1.isMonthDay(this.month)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

}
