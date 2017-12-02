package com.sb.anyfigure.tuple;

import java.util.Objects;

public class Tuple5<T1, T2, T3, T4, T5> extends
		scala.Tuple5<T1, T2, T3, T4, T5> {

	public Tuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
		super(_1, _2, _3, _4, _5);
	}

	public Tuple5(Tuple5<T1, T2, T3, T4, T5> t) {
		this(t._1(), t._2(), t._3(), t._4(), t._5());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this._1(), this._2(), this._3(), this._4(),
				this._5());
	}

	@Override
	public boolean equals(Object x$1) {
		if (x$1 == null || !(x$1 instanceof Tuple5)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Tuple5<T1, T2, T3, T4, T5> o2 = (Tuple5<T1, T2, T3, T4, T5>) x$1;
		return this._1().equals(o2._1()) && this._2().equals(o2._2())
				&& this._3().equals(o2._3()) && this._4().equals(o2._4())
				&& this._5().equals(o2._5());
	}
}
