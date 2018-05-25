package com.cglee079.cointelebot.coin;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class BitfinexPooler extends ApiPooler{
	public JSONObject getCoin(String coin) throws ServerErrorException{
		String param = coinParam.get(coin);
		
		String url = "https://api.bitfinex.com/v2/ticker/" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONArray jsonArr= new JSONArray(response);
			JSONObject coinObj = new JSONObject();
//			coinObj.put("bid", jsonArr.get(0));
//			coinObj.put("bid_size", jsonArr.get(1));
//			coinObj.put("ask", jsonArr.get(2));
//			coinObj.put("ask_size", jsonArr.get(3));
//			coinObj.put("bid", jsonArr.get(4));
//			coinObj.put("bid", jsonArr.get(5));
			coinObj.put("first", 0);
			coinObj.put("last", jsonArr.get(6));
			coinObj.put("volume", jsonArr.get(7));
			coinObj.put("high", jsonArr.get(8));
			coinObj.put("low", jsonArr.get(9));
			coinObj.put("errorCode", 0);
			coinObj.put("errorMsg", "");
			coinObj.put("result", "success");
			retryCnt = 0;
			return coinObj;
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("Bitfinex Server Error : " + e.getMessage());
			}
		}
	}
	
}
