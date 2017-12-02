package com.sb.ff.framework.test;

import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest {
	HBaseTestingUtility utility;

	@Before
	public void setup() throws Exception {
		utility = new HBaseTestingUtility();
		utility.getConfiguration().addResource("hbase-site-local.xml");
		utility.getConfiguration().reloadConfiguration();
		utility.startMiniCluster();
	}

	@Test
	public void testTable1() throws Exception {
		byte[] RAW_TABLE1 = Bytes.toBytes("table1");
		byte[] RAW_CF = Bytes.toBytes("CF");
		byte[] RAW_CQ1 = Bytes.toBytes("CQ-1");
		byte[] RAW_CQ2 = Bytes.toBytes("CQ-2");

		HTable table = utility.createTable(RAW_TABLE1, RAW_CF);
		Put put = new Put(RAW_CF);

		for (int i = 0; i < 10; i++) {

			byte[] row = Bytes.toBytes(i++);
			int j = 0;
			put.addColumn(row, RAW_CQ1, Bytes.toBytes(j++));
			put.addColumn(row, RAW_CQ2, Bytes.toBytes(j++));
		}

		table.put(put);
		for (int i = 0; i < 10; i++) {
			byte[] row = Bytes.toBytes(i);

			Get get = new Get(Bytes.toBytes(i));
			get.addColumn(RAW_CF, RAW_CQ1);
			get.addColumn(RAW_CF, RAW_CQ2);
			Result result = table.get(get);
			Assert.assertEquals(row, result.getRow());
			List<Cell> cell1L = result.getColumnCells(RAW_CF, RAW_CQ1);
			Assert.assertEquals(1, cell1L.size());
			Cell c1 = cell1L.get(0);
			Assert.assertEquals(Bytes.toBytes(0), c1.getValueArray());

			List<Cell> cell2L = result.getColumnCells(RAW_CF, RAW_CQ2);
			Assert.assertEquals(1, cell2L.size());
			Cell c2 = cell2L.get(0);
			Assert.assertEquals(Bytes.toBytes(1), c2.getValueArray());

		}

	}
}
