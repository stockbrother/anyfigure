package com.sb.anyfigure;

public class Org implements java.io.Serializable {

	private String code;

	private Org(String code) {
		this.code = code;
	}

	public static Org valueOf(String orgCode) {
		return new Org(orgCode);

	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return this.code;

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !Org.class.isInstance(obj)) {
			return false;
		}
		Org o2 = (Org) obj;
		return this.code.equals(o2.code);
	}

	@Override
	public int hashCode() {
		return this.code.hashCode();
	}

}
