package com.sb.anyfigure.tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.MonthDay;

import scala.Tuple3;

public class DateStringDecimalTuple extends
		Tuple3<LocalDate, String, BigDecimal> {

	public DateStringDecimalTuple(LocalDate _1, String _2, BigDecimal _3) {
		super(_1, _2, _3);
	}

	public LocalDate getDate() {
		return this._1();
	}

	public boolean isAttribute(String name) {
		return this._2().equals(name);
	}

	public boolean isMonthDay(MonthDay mday) {
		return mday.getMonth() == this._1().getMonth()
				&& mday.getDayOfMonth() == this._1().getDayOfMonth();
	}
}
