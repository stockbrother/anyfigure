package com.sb.anyfigure.csv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvHeader extends CsvRow {

	private static DateTimeFormatter FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd");

	public CsvHeader(int index, String[] row) {
		super(index, row);
	}

	public List<LocalDate> getAllAsLocalDate() {
		List<LocalDate> ldL = this.getAllAsLocalDate(FORMATTER);
		return ldL;
	}

}
