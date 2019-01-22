package com.cglee079.coinchatbot.coin;

import org.json.JSONObject;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.exception.ServerErrorException;

public class HadaxPooler extends ApiPooler{
	public JSONObject getCoin(Coin coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		JSONObject coinObj = this.getCurrentCoin(param);
		
		return coinObj;
	}
	
	public JSONObject getCurrentCoin(String param) throws ServerErrorException {
		String url = "https://api.hadax.com/market/detail/merged?symbol=" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		JSONObject responseObj 	= null;
		JSONObject coinObj 		= null;
		JSONObject newCoinObj 	= null;
		try {
			response 	= httpClient.get(url);
			responseObj = new JSONObject(response);
			newCoinObj 	= new JSONObject();
			if(responseObj.getString("status").equals("ok")) {
				newCoinObj.put("result", "success");
				newCoinObj.put("errorCode", 0);
				newCoinObj.put("errorMsg", "");
			} else {
				newCoinObj.put("result", "error");
				newCoinObj.put("errorCode", 1);
				newCoinObj.put("errorMsg", responseObj.getString("err-code")  + "\t" + responseObj.getString("err-msg"));
			}
			
			coinObj = responseObj.getJSONObject("tick");
			newCoinObj.put("volume", coinObj.getDouble("vol"));
			newCoinObj.put("first", coinObj.getDouble("open"));
			newCoinObj.put("last", coinObj.getDouble("close"));
			newCoinObj.put("high", coinObj.getDouble("high"));
			newCoinObj.put("low", coinObj.getDouble("low"));
			
			
			return newCoinObj;
		} catch (Exception e) {
			throw new ServerErrorException("Hadax server error: " + e.getMessage());
		}
		
	}
}
