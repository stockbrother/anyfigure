package com.sb.anyfigure.csv;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sb.anyfigure.DataFile;
import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;

public class DataFileReader {

	private CsvHeader header;

	private List<LocalDate> headerDateList;

	private DataFile dataFile;

	private static String SUFFIX_UNIT_START = "(";
	private static String SUFFIX_UNIT_END = ")";

	private static String SUFFIX_UNIT = SUFFIX_UNIT_START + "万元"
			+ SUFFIX_UNIT_END;

	private static Map<String, BigDecimal> UNITMAP = new HashMap<String, BigDecimal>();

	static {
		UNITMAP.put("万元", BigDecimal.valueOf(10000));
	}

	public DataFileReader(DataFile dataFile) {
		this.dataFile = dataFile;
	}

	public List<OrgDateGroupStringDecimalTuple> readAll() {
		CsvReader reader = this.dataFile.buildCsvReader();
		String[] row1 = reader.readNext();
		header = new CsvHeader(0, row1);
		header.assertTitle("报告日期");
		this.headerDateList = this.header.getAllAsLocalDate();

		List<OrgDateGroupStringDecimalTuple> rt = new ArrayList<OrgDateGroupStringDecimalTuple>();
		while (true) {
			CsvRow row = reader.readNextAsRow();
			if (row == null) {
				break;
			}
			this.append(rt, row);
		}
		return rt;

	}

	public List<OrgDateGroupStringDecimalTuple> append(
			List<OrgDateGroupStringDecimalTuple> rt, CsvRow row) {
		String title = row.getTitle();
		String name = title;
		BigDecimal unit = BigDecimal.ONE;
		if (title.endsWith(SUFFIX_UNIT_END)) {
			int indexStart = name.lastIndexOf(SUFFIX_UNIT_START);
			name = title.substring(0, indexStart);
			String unitName = title.substring(indexStart + 1, title.length()
					- SUFFIX_UNIT_END.length());
			BigDecimal unitD = UNITMAP.get(unitName);
			if (unitD == null) {
				throw new RuntimeException("not found unit:" + unitName);
			}
			unit = unitD;
		}

		for (int i = 0; i < row.getSize(); i++) {

			String valueI = row.get(i);
			BigDecimal dvalue = null;
			if (!valueI.endsWith("--")) {
				try {
					dvalue = new BigDecimal(valueI);
					dvalue = dvalue.multiply(unit);
				} catch (NumberFormatException e) {
					throw new RuntimeException("value:" + valueI + ",row:"
							+ row, e);
				}

			}
			OrgDateGroupStringDecimalTuple rtI = new OrgDateGroupStringDecimalTuple(
					this.dataFile.getCompanyCode(),//
					this.headerDateList.get(i),//
					this.dataFile.getTableType(),//
					name, dvalue);
			rt.add(rtI);
		}

		return rt;
	}
}
