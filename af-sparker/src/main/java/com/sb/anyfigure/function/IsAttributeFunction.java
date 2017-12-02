package com.sb.anyfigure.function;

import org.apache.spark.api.java.function.Function;

import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;

public class IsAttributeFunction implements
		Function<OrgDateGroupStringDecimalTuple, Boolean> {

	private String attribute;

	public IsAttributeFunction(String name) {
		this.attribute = name;
	}

	@Override
	public Boolean call(OrgDateGroupStringDecimalTuple v1) throws Exception {
		if (v1.isString(this.attribute)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

}
