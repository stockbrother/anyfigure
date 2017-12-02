package com.sb.anyfigure.tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.MonthDay;

import com.sb.anyfigure.Group;
import com.sb.anyfigure.Org;

public class OrgDateGroupStringDecimalTuple extends
		Tuple5<Org, LocalDate, Group, String, BigDecimal> {

	public OrgDateGroupStringDecimalTuple(Org _1, LocalDate _2, Group _3,
			String _4, BigDecimal _5) {
		super(_1, _2, _3, _4, _5);
	}

	public OrgDateGroupStringDecimalTuple(OrgDateGroupStringDecimalTuple key) {
		super(key);
	}

	public BigDecimal getValue(BigDecimal def) {
		if (this._5() == null) {
			return def;
		}
		return this._5();
	}

	public BigDecimal getValue() {
		return this._5();
	}

	public Org getOrg() {
		return this._1();
	}

	public Group getGroup() {
		return this._3();
	}

	public LocalDate getDate() {
		return this._2();
	}

	public boolean isString(String name) {
		return name.equals(this._4());
	}

	public boolean isMonthDay(MonthDay mday) {
		return mday.getMonth() == this._2().getMonth()
				&& mday.getDayOfMonth() == this._2().getDayOfMonth();
	}

	public String getString() {
		return this._4();

	}

}
