package com.cglee079.coinchatbot.coin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.coinchatbot.exception.ServerErrorException;

public class BittrexPooler extends ApiPooler{
	private JSONArray coinObjs = null;
	private String errMessage;
	
	
	public BittrexPooler() throws ServerErrorException {
		getCoins();
	}

	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		if(coinObjs != null) {
			JSONObject coinObj = null;
			for(int i =0; i < coinObjs.length(); i++) {
				coinObj = coinObjs.getJSONObject(i);
				if(coinObj.getString("MarketName").equals(param)) {
					JSONObject newCoinObj = new JSONObject();
					newCoinObj.put("first", 0);
					newCoinObj.put("last", coinObj.getDouble("Last"));
					newCoinObj.put("high", coinObj.getDouble("High"));
					newCoinObj.put("low", coinObj.getDouble("Low"));
					newCoinObj.put("volume", coinObj.getDouble("Volume"));
					newCoinObj.put("errorCode", 0);
					newCoinObj.put("errorMsg", "");
					newCoinObj.put("result", "success");
					return newCoinObj;
				}
			}
			return null;
		} else {
			throw new ServerErrorException("Bittrex Server Error : " + errMessage);
		}
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() throws ServerErrorException {
		String url = "https://bittrex.com/api/v1.1/public/getmarketsummaries";
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj= new JSONObject(response);
			if(!jsonObj.getBoolean("success")) {
				throw new Exception("Bitrrex Server Error : " + jsonObj.getString("message"));
			} 
			
			coinObjs = jsonObj.getJSONArray("result");
			
		} catch (Exception e) {
			coinObjs 	= null;
			errMessage 	= e.getMessage();
		}
	}
}
