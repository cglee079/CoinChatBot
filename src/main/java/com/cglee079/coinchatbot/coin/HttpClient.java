package com.cglee079.coinchatbot.coin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cglee079.coinchatbot.config.log.MyLog;

public class HttpClient {
	public String get(String url) throws Exception {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			// optional default is GET
			con.setRequestMethod("GET");
			con.setConnectTimeout(5 * 1000);
			con.setReadTimeout(5 * 1000);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();
		} catch (MalformedURLException e) {
			MyLog.e(this.getClass(), e.getMessage());
			e.printStackTrace();
		} 		
		return null;
	}
}
