package com.sb.anyfigure.tuple;

import java.time.LocalDate;

import scala.Tuple2;

public class DateStringTuple extends Tuple2<LocalDate, String> {

	public DateStringTuple(LocalDate _1, String _2) {
		super(_1, _2);
	}

}
