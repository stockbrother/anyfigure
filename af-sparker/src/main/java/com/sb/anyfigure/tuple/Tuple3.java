package com.sb.anyfigure.tuple;

import java.util.Objects;

public class Tuple3<T1, T2, T3> extends scala.Tuple3<T1, T2, T3> {

	public Tuple3(T1 _1, T2 _2, T3 _3) {
		super(_1, _2, _3);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this._1(), this._2(), this._3());
	}

	@Override
	public boolean equals(Object x$1) {
		if (x$1 == null || !(x$1 instanceof Tuple3)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Tuple3<T1, T2, T3> o2 = (Tuple3<T1, T2, T3>) x$1;
		return this._1().equals(o2._1()) && this._2().equals(o2._2())
				&& this._3().equals(o2._3());
	}
}
