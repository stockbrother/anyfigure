package com;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Java8Tester {

	public static void main(String[] args) {
		BigDecimal b1 = new BigDecimal(574350000);
		BigDecimal b2 = new BigDecimal("6596500000");
		BigDecimal b3 = b1.divide(b2, 2, RoundingMode.HALF_EVEN);
		System.out.println(b3);

	}

	private void sortUsingJava8(List<String> names) {
		Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
	}

	private void test() {
		List names = new ArrayList();

		names.add("Mahesh");
		names.add("Suresh");
		names.add("Ramesh");
		names.add("Naresh");
		names.add("Kalpesh");

		names.forEach(System.out::println);
	}
}
