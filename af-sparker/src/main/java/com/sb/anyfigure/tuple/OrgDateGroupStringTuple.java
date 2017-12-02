package com.sb.anyfigure.tuple;

import java.time.LocalDate;

import scala.Tuple4;

import com.sb.anyfigure.Group;
import com.sb.anyfigure.Org;

public class OrgDateGroupStringTuple extends
		Tuple4<Org, LocalDate, Group, String> {

	public OrgDateGroupStringTuple(Org org, LocalDate date, Group grp, String s) {
		super(org, date, grp, s);
	}

}
