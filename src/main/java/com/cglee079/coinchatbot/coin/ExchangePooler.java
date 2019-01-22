package com.cglee079.coinchatbot.coin;

import org.json.JSONObject;

import com.cglee079.coinchatbot.exception.ServerErrorException;

public class ExchangePooler extends ApiPooler{
	public double usd2krw() throws ServerErrorException {
		String url = "http://data.fixer.io/api/latest?access_key=f8e8de4219acbfc156083b7a039acf28";
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject responseObj = new JSONObject(response);
			JSONObject rateObj = responseObj.getJSONObject("rates");
			double rate = rateObj.getDouble("KRW")/ rateObj.getDouble("USD");
			return rate;
		} catch (Exception e) {
			throw new ServerErrorException("Exchange rate server error " + e.getMessage());
		}
	}
}
