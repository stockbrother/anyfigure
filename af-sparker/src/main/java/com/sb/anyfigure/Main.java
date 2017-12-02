package com.sb.anyfigure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import com.sb.anyfigure.tuple.OrgDateGroupStringDecimalTuple;

public class Main {

	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf().setAppName("Attributes")
				.setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(sparkConf);
		SQLContext sqlc = new SQLContext(jsc);

		File dataDir = new File("src/test/resources");
		DataLoader loader = new DataLoader(jsc, dataDir);

		JavaRDD<OrgDateGroupStringDecimalTuple> jdd = loader.loadAll();
		JavaRDD<Row> jdd2 = jdd
				.map(new Function<OrgDateGroupStringDecimalTuple, Row>() {

					@Override
					public Row call(OrgDateGroupStringDecimalTuple v1)
							throws Exception {
						
						return RowFactory.create(v1._1().getCode(),
								new java.sql.Date(v1._2().getYear(), v1._2()
										.getMonth().getValue(), v1._2()
										.getDayOfMonth()), v1._3().ordinal(),
								v1._4(), v1._5());//
					}
				});

		jdd2.cache();

		List<StructField> fields = new ArrayList<StructField>();
		fields.add(DataTypes.createStructField("org", DataTypes.StringType,
				true));
		fields.add(DataTypes
				.createStructField("date", DataTypes.DateType, true));
		fields.add(DataTypes.createStructField("group", DataTypes.IntegerType,
				true));
		fields.add(DataTypes.createStructField("name", DataTypes.StringType,
				true));
		fields.add(DataTypes.createStructField("value",
				DataTypes.createDecimalType(), true));
		StructType schema = DataTypes.createStructType(fields);

		DataFrame df = sqlc.createDataFrame(jdd2, schema);
		df.registerTempTable("attributes");

		DataFrame df2 = sqlc.sql("select * from attributes where name='营业收入'");
		df2.show();
	}

}
