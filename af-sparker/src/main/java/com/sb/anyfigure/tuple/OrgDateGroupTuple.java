package com.sb.anyfigure.tuple;

import java.time.LocalDate;

import com.sb.anyfigure.Group;
import com.sb.anyfigure.Org;

public class OrgDateGroupTuple extends Tuple3<Org, LocalDate, Group> {

	public OrgDateGroupTuple(Org org, LocalDate date, Group grp) {
		super(org, date, grp);
	}

}
