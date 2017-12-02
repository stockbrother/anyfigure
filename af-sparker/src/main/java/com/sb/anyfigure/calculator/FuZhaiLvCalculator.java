package com.sb.anyfigure.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import com.sb.anyfigure.CalculateContext;
import com.sb.anyfigure.Calculator;
import com.sb.anyfigure.JavaRddWraper;
import com.sb.anyfigure.function.IsAttributeFunction;
import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;
import com.sb.anyfigure.tuple.OrgDateGroupTuple;

public class FuZhaiLvCalculator implements
		Calculator<OrgDateGroupStringDecimalTuple> {
	private static class PF
			implements
			PairFunction<OrgDateGroupStringDecimalTuple, OrgDateGroupTuple, BigDecimal> {

		@Override
		public Tuple2<OrgDateGroupTuple, BigDecimal> call(
				OrgDateGroupStringDecimalTuple t) throws Exception {
			OrgDateGroupTuple key = new OrgDateGroupTuple(t.getOrg(),
					t.getDate(), t.getGroup());

			return new Tuple2<OrgDateGroupTuple, BigDecimal>(key, t.getValue());

		}

	}

	private static class MF
			implements
			Function<Tuple2<OrgDateGroupTuple, Tuple2<BigDecimal, BigDecimal>>, OrgDateGroupStringDecimalTuple> {

		@Override
		public OrgDateGroupStringDecimalTuple call(
				Tuple2<OrgDateGroupTuple, Tuple2<BigDecimal, BigDecimal>> v1)
				throws Exception {
			BigDecimal zczj = v1._2._1;
			BigDecimal fzhj = v1._2._2;
			BigDecimal value = null;
			value = fzhj.divide(zczj, 2, RoundingMode.HALF_EVEN);
			// System.out.println("call" + fzhj + "/" + zczj + "=" + value);

			return new OrgDateGroupStringDecimalTuple(v1._1._1(), v1._1._2(),
					v1._1._3(), "负债率", value);
		}

	}

	private static class SF implements
			Function<OrgDateGroupStringDecimalTuple, String> {

		@Override
		public String call(OrgDateGroupStringDecimalTuple v1) throws Exception {
			//
			return v1._1().getCode() + v1._3();
		}

	}

	@Override
	public JavaRDD<OrgDateGroupStringDecimalTuple> calculate(CalculateContext cs) {

		JavaRDD<OrgDateGroupStringDecimalTuple> zczj = cs.ogdsdRdd
				.filter(new IsAttributeFunction("资产总计"));
		// System.out.println("zczj::");
		JavaRddWraper.dump(zczj);

		JavaPairRDD<OrgDateGroupTuple, BigDecimal> zczj2 = zczj
				.mapToPair(new PF());
		// System.out.println("zczj2::" + zczj2.count());

		JavaRddWraper.dump(zczj2);

		JavaRDD<OrgDateGroupStringDecimalTuple> fzhj = cs.ogdsdRdd
				.filter(new IsAttributeFunction("负债合计"));

		// System.out.println("fzhj::");
		JavaRddWraper.dump(fzhj);

		JavaPairRDD<OrgDateGroupTuple, BigDecimal> fzhj2 = fzhj
				.mapToPair(new PF());
		// System.out.println("fzhj2::" + fzhj2.count());
		JavaRddWraper.dump(fzhj2);

		JavaPairRDD<OrgDateGroupTuple, Tuple2<BigDecimal, BigDecimal>> jo = zczj2
				.join(fzhj2);
		// System.out.println("jo::" + jo.count());
		JavaRddWraper.dump(jo);
		return jo.map(new MF()).sortBy(new SF(), false, 1);//

	}
}
