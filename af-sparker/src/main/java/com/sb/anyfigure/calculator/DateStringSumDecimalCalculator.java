package com.sb.anyfigure.calculator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import com.sb.anyfigure.CalculateContext;
import com.sb.anyfigure.Calculator;
import com.sb.anyfigure.tuple.DateStringDecimalTuple;
import com.sb.anyfigure.tuple.DateStringTuple;
import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;

public class DateStringSumDecimalCalculator implements Calculator<DateStringDecimalTuple> {
	public static class PF
			implements
			PairFunction<OrgDateGroupStringDecimalTuple, DateStringTuple, BigDecimal> {
		@Override
		public Tuple2<DateStringTuple, BigDecimal> call(
				OrgDateGroupStringDecimalTuple t) throws Exception {
			DateStringTuple key = new DateStringTuple(t.getDate(),
					t.getString());
			return new Tuple2<DateStringTuple, BigDecimal>(key,
					t.getValue(BigDecimal.ZERO));
		}
	}

	private static class MF
			implements
			Function<Tuple2<DateStringTuple, BigDecimal>, DateStringDecimalTuple> {
		@Override
		public DateStringDecimalTuple call(
				Tuple2<DateStringTuple, BigDecimal> v1) throws Exception {
			return new DateStringDecimalTuple(v1._1._1, v1._1._2, v1._2);
		}
	}

	private static class RF implements
			Function2<BigDecimal, BigDecimal, BigDecimal> {
		@Override
		public BigDecimal call(BigDecimal v1, BigDecimal v2) throws Exception {
			return v1.add(v2);
		}
	}

	private static class SF implements
			Function<DateStringDecimalTuple, LocalDate> {
		@Override
		public LocalDate call(DateStringDecimalTuple v1) throws Exception {
			return v1.getDate();
		}
	}

	@Override
	public JavaRDD<DateStringDecimalTuple> calculate(CalculateContext cc) {

		// zc
		JavaRDD<DateStringDecimalTuple> rt = cc.ogdsdRdd.mapToPair(new PF())
				.reduceByKey(new RF()).map(new MF()).sortBy(new SF(), false, 2);
		return rt;
	}

}
