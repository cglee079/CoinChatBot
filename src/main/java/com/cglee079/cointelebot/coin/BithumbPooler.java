package com.cglee079.cointelebot.coin;

import org.json.JSONObject;

import com.cglee079.cointelebot.exception.ServerErrorException;

public class BithumbPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		String url = "https://api.bithumb.com/public/ticker/" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj = new JSONObject(response);
			if(jsonObj.getInt("status") == 0000){
				JSONObject coinObj = new JSONObject();
				JSONObject data = jsonObj.getJSONObject("data");
				coinObj.put("errorCode", jsonObj.getInt("status"));
				coinObj.put("errorMsg", "");
				coinObj.put("result", "success");
				coinObj.put("volume", data.getDouble("volume_1day"));
				coinObj.put("first", data.getDouble("opening_price"));
				coinObj.put("last", data.getDouble("closing_price"));
				coinObj.put("high", data.getDouble("max_price"));
				coinObj.put("low", data.getDouble("min_price"));
				
				return coinObj;				
			} else{
				throw new ServerErrorException("Bithumb Server Error", jsonObj.getInt("status"));
			}
		} catch (Exception e) {
			throw new ServerErrorException("Bithumb Server Error : " + e.getMessage());
		}
	}
}