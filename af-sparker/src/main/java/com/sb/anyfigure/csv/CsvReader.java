package com.sb.anyfigure.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import au.com.bytecode.opencsv.CSVReader;

public class CsvReader {

	private CSVReader reader;

	private int nextIndex;

	private RowCorrecter rowCorrecter;

	protected CsvReader(Reader file) {
		reader = new CSVReader(file);
	}

	public static CsvReader newInstance(File file, Charset cs, RowCorrecter rc) {
		Reader fr;
		try {
			fr = new InputStreamReader(new FileInputStream(file), cs);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		CsvReader rt = new CsvReader(fr);
		rt.rowCorrecter = rc;
		return rt;
	}

	public String[] readNext() {
		try {
			String[] rt = this.reader.readNext();
			if (this.rowCorrecter != null) {
				rt = this.rowCorrecter.correct(rt);//
			}
			this.nextIndex++;
			return rt;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public CsvRow readNextAsRow() {
		String[] row = this.readNext();

		if (row == null) {
			return null;
		}

		return new CsvRow(this.nextIndex - 1, row);
	}

	public JavaRDD<CsvRow> readAsCsvRowRdd(JavaSparkContext jsc) {
		List<CsvRow> rowL = new ArrayList<CsvRow>();
		while (true) {
			CsvRow row = this.readNextAsRow();
			if (row == null) {
				break;
			}
			rowL.add(row);
		}

		return jsc.parallelize(rowL);
	}

}
