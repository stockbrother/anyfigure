package com.sb.anyfigure.csv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvRow {

	private int index;

	private String[] row;

	public CsvRow(int index, String[] row) {
		this.index = index;
		this.row = row;
	}

	public String getTitle() {
		return this.row[0];
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return row.length - 2;
	}

	public String get(int i) {
		return row[i + 1];
	}

	public void assertTitle(String title) {
		if (!title.equals(this.getTitle())) {
			throw new RuntimeException("expected:" + title + ",actual:"
					+ this.getTitle());
		}
	}

	public List<LocalDate> getAllAsLocalDate(DateTimeFormatter formatter) {
		List<LocalDate> rt = new ArrayList<LocalDate>();
		for (int i = 0; i < this.getSize(); i++) {
			LocalDate ldI = this.getAsLocalDate(i, formatter);
			rt.add(ldI);
		}
		return rt;
	}

	public LocalDate getAsLocalDate(int idx, DateTimeFormatter formatter) {
		String value = this.get(idx);
		TemporalAccessor ta = formatter.parse(value);
		return LocalDate.from(ta);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("").append(index).append(",").append(Arrays.asList(this.row));
		return sb.toString();
	}

}
