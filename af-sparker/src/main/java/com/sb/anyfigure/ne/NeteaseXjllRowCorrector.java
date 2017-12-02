package com.sb.anyfigure.ne;

import com.sb.anyfigure.csv.TrimLineRowCorrector;

public class NeteaseXjllRowCorrector extends TrimLineRowCorrector {

	@Override
	public String[] correct(String[] rt) {

		super.correct(rt);
		if (rt[0].startsWith("净利润")) {
			// ignore all the rows below this line.
			return null;
		}
		return rt;
	}

}
