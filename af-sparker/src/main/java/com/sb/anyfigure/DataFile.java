package com.sb.anyfigure;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.sb.anyfigure.csv.CsvReader;
import com.sb.anyfigure.ne.NeteaseXjllRowCorrector;

public class DataFile {

	private static int SUFFIX_LENGTH = ".csv".length();
	private static int COMPANYCODE_LENGTH = "000000".length();
	private static int SUFFIX2_LENGTH = COMPANYCODE_LENGTH + SUFFIX_LENGTH;

	private static Map<String, Group> TABLETYPES = new HashMap<String, Group>();

	static {
		TABLETYPES.put("zcfzb", Group.ZCFZB);
		TABLETYPES.put("lrb", Group.LRB);
		TABLETYPES.put("xjllb", Group.XJLLB);
	}

	private Org companyCode;

	private Group tableType;

	private File file;

	public DataFile(File file) {
		this.file = file;
		String fileName = file.getName();

		String tableCode = fileName.substring(0, fileName.length()
				- SUFFIX2_LENGTH);

		this.tableType = TABLETYPES.get(tableCode);
		if (tableType == null) {
			throw new RuntimeException("no table type with code:" + tableCode);
		}

		String orgCode =		fileName.substring(tableCode.length(),
				fileName.length() - SUFFIX_LENGTH);
				this.companyCode = Org.valueOf(orgCode);

	}

	public Org getCompanyCode() {
		return companyCode;
	}

	public Group getTableType() {
		return tableType;
	}

	public CsvReader buildCsvReader() {
		CsvReader rt = null;
		Charset cs = Charset.forName("GBK");

		switch (this.tableType) {
		case XJLLB:
			rt = CsvReader.newInstance(this.file, cs,
					new NeteaseXjllRowCorrector());
			break;
		default:
			rt = CsvReader.newInstance(this.file, cs, null);//
		}

		return rt;
	}
}
