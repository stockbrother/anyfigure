package com.sb.anyfigure.ne;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.sb.anyfigure.Org;
import com.sb.anyfigure.OrgInfo;
import com.sb.anyfigure.csv.CsvReader;

public class NeteaseCollector {

	private File dataFolder;

	private File neteaseFolder;

	private CloseableHttpClient httpclient;

	private HttpHost target;

	private HttpHost proxy;

	private RequestConfig config;

	private List<OrgInfo> orgInfoList;

	public NeteaseCollector(File dataFolder) {
		this.dataFolder = dataFolder;
		this.neteaseFolder = new File(this.dataFolder, "netease");
	}

	public static void main(String[] args) throws Exception {
		new NeteaseCollector(new File("data")).start();
	}

	public List<OrgInfo> loadOrgInfo() {
		List<OrgInfo> rt = new ArrayList<OrgInfo>();
		File file = new File(this.dataFolder, "org-info.csv");
		CsvReader reader = CsvReader.newInstance(file,
				Charset.forName("UTF-8"), null);

		while (true) {
			String[] row = reader.readNext();
			if (row == null) {
				break;
			}
			OrgInfo oi = new OrgInfo(Org.valueOf(row[0]), row[1]);
			rt.add(oi);
		}
		return rt;
	}

	public void start() throws Exception {
		this.orgInfoList = this.loadOrgInfo();
		httpclient = HttpClients.custom().build();
		try {
			// http://quotes.money.163.com/service/zcfzb_600305.html
			target = new HttpHost("quotes.money.163.com", 80, "http");
			proxy = new HttpHost("web-proxy.atl.hp.com", 8080);
			for (OrgInfo oi : this.orgInfoList) {
				this.collectFor(oi);
			}
			config = RequestConfig.custom().setProxy(proxy).build();

		} finally {
			httpclient.close();
		}
	}

	public void collectFor(OrgInfo oi) throws Exception {
		String type = "zcfzb";
		File file = new File(this.neteaseFolder, type + oi.getOrg().getCode());
		if (file.exists()) {
			System.out.println("file exist:" + file);
		} else {

			collectFor(oi, type, file);
		}

	}

	public void collectFor(OrgInfo oi, String type, File file) throws Exception {

		HttpGet httpget = new HttpGet("/service/" + type + "_"
				+ oi.getOrg().getCode() + ".html");
		httpget.setConfig(config);

		System.out.println("Executing request " + httpget.getRequestLine()
				+ " to " + target + " via " + proxy);

		CloseableHttpResponse response = httpclient.execute(target, httpget);

		try {

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (response.getStatusLine().getStatusCode() == 200) {
				FileOutputStream os = new FileOutputStream(file);
				response.getEntity().writeTo(os);
			}

		} finally {
			response.close();
		}
	}
}
