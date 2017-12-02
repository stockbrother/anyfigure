package com.sb.anyfigure;

import java.util.HashMap;
import java.util.Map;

public class Calculators<T> {

	private Map<String, Calculator<T>> attributeMap = new HashMap<String, Calculator<T>>();

	public Calculator<T> get(String attribute) {

		return attributeMap.get(attribute);//

	}

	public void add(Calculator<T> rdd) {

	}

}
