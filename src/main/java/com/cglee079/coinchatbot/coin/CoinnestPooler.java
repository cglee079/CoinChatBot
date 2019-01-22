package com.cglee079.coinchatbot.coin;

import org.json.JSONObject;

import com.cglee079.coinchatbot.exception.ServerErrorException;

public class CoinnestPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		String url = "https://api.coinnest.co.kr/api/pub/ticker?coin=" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject coinObj = new JSONObject();
			JSONObject data = new JSONObject(response);
			coinObj.put("errorCode", 0);
			coinObj.put("errorMsg", "");
			coinObj.put("result", "success");
			coinObj.put("volume", data.getDouble("vol"));
			coinObj.put("last", data.getDouble("last"));
			coinObj.put("high", data.getDouble("high"));
			coinObj.put("low", data.getDouble("low"));
			
			return coinObj;				
		} catch (Exception e) {
			throw new ServerErrorException("Coinnest Server Error : " + e.getMessage());
		}
	}
}