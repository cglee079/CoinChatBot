package com.cglee079.cointelebot.coin;

import org.json.JSONObject;

import com.cglee079.cointelebot.exception.ServerErrorException;

public class BittrexPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		JSONObject coinObj = getCurrentCoin(param);
		
		return coinObj;
	}
	
	public JSONObject getCurrentCoin(String param) throws ServerErrorException {
		String url = "https://bittrex.com/api/v1.1/public/getmarketsummary?market=" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj= new JSONObject(response);
			JSONObject coinObj = new JSONObject();
			coinObj.put("first", 0);
			coinObj.put("last", jsonObj.getJSONArray("result").getJSONObject(0).getDouble("Last"));
			coinObj.put("high", jsonObj.getJSONArray("result").getJSONObject(0).getDouble("High"));
			coinObj.put("low", jsonObj.getJSONArray("result").getJSONObject(0).getDouble("Low"));
			coinObj.put("volume", jsonObj.getJSONArray("result").getJSONObject(0).getDouble("Volume"));
			coinObj.put("errorCode", 0);
			coinObj.put("errorMsg", jsonObj.getString("message"));
			
			if(jsonObj.getBoolean("success")) {
				coinObj.put("result", "success");
			} else {
				coinObj.put("result", "error");
			}
			
			retryCnt = 0;
			return coinObj;
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCurrentCoin(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("Bitrrex Server Error : " + e.getMessage());
			}
		}
	}
}
