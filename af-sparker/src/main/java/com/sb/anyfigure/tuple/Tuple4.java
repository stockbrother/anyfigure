package com.sb.anyfigure.tuple;

import java.util.Objects;

public class Tuple4<T1, T2, T3, T4> extends scala.Tuple4<T1, T2, T3, T4> {

	public Tuple4(T1 _1, T2 _2, T3 _3, T4 _4) {
		super(_1, _2, _3, _4);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this._1(), this._2(), this._3(), this._4());
	}

	@Override
	public boolean equals(Object x$1) {
		if (x$1 == null || !(x$1 instanceof Tuple4)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Tuple4<T1, T2, T3, T4> o2 = (Tuple4<T1, T2, T3, T4>) x$1;
		return this._1().equals(o2._1()) && this._2().equals(o2._2())
				&& this._3().equals(o2._3()) && this._4().equals(o2._4());
	}
}
