package com.sb.anyfigure;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import com.sb.anyfigure.csv.DataFileReader;
import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;

public class DataLoader {

	private JavaSparkContext javaSparkContext;

	private File dataDir;

	public DataLoader(JavaSparkContext jsc, File dataDir) {
		this.javaSparkContext = jsc;
		this.dataDir = dataDir;
	}

	public JavaRDD<OrgDateGroupStringDecimalTuple> loadAll() {
		File[] fl = this.dataDir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				//
				return pathname.getName().endsWith(".csv");//
			}
		});
		JavaRDD<OrgDateGroupStringDecimalTuple> rt = this.javaSparkContext
				.parallelize(new ArrayList<OrgDateGroupStringDecimalTuple>());
		for (int i = 0; i < fl.length; i++) {
			DataFile df = new DataFile(fl[i]);
			JavaRDD<OrgDateGroupStringDecimalTuple> rddI = this.loadDataFile(df);
			rt = rt.union(rddI);
		}

		return rt;
	}

	public JavaRDD<OrgDateGroupStringDecimalTuple> loadDataFile(DataFile df) {

		DataFileReader reader2 = new DataFileReader(
				df);
		List<OrgDateGroupStringDecimalTuple> dcaL = reader2.readAll();
		JavaRDD<OrgDateGroupStringDecimalTuple> rt = this.javaSparkContext
				.parallelize(dcaL);

		return rt;

	}
}
