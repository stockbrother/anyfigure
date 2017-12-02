package com.sb.anyfigure;

public class OrgInfo {

	private Org org;

	private String name;

	public OrgInfo(Org org, String name) {
		this.org = org;
		this.name = name;
	}

	public Org getOrg() {
		return org;
	}

	public String getName() {
		return name;
	}
}
