package com.sb.anyfigure.csv;

import org.apache.commons.lang.StringUtils;

public class TrimLineRowCorrector implements RowCorrecter {

	@Override
	public String[] correct(String[] rt) {
		if (rt == null) {
			return rt;
		}
		if (rt.length > 0) {
			rt[0] = StringUtils.stripStart(rt[0], null);
		}
		if (rt.length > 1) {
			rt[rt.length - 1] = StringUtils.stripEnd(rt[rt.length - 1], null);
		}
		return rt;

	}

}
