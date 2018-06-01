package com.cglee079.cointelebot.coin;

import org.json.JSONObject;

import com.cglee079.cointelebot.exception.ServerErrorException;

public class OkexPooler extends ApiPooler{
	private int i = 0;
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		JSONObject coinObj = getCurrentCoin(param);
		
		return coinObj;
	}
	
	public JSONObject getCurrentCoin(String param) throws ServerErrorException {
		String url = "";
		if(param.endsWith("usd")) {
			url = "https://www.okex.com/api/v1/future_ticker.do?contract_type=this_week&symbol=" + param;
		} else if(param.endsWith("btc")) {
			url = "https://www.okex.com/api/v1/ticker.do?symbol=" + param;
		}
		
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject responseObj 	= new JSONObject(response);
			JSONObject coinObj		= responseObj.getJSONObject("ticker");
			JSONObject newCoinObj 	= new JSONObject();
			newCoinObj.put("first", 0);
			newCoinObj.put("last", coinObj.getDouble("last"));
			newCoinObj.put("high", coinObj.getDouble("high"));
			newCoinObj.put("low", coinObj.getDouble("low"));
			newCoinObj.put("volume", coinObj.getDouble("vol"));
			newCoinObj.put("errorCode", 0);
			newCoinObj.put("errorMsg", "");
			newCoinObj.put("result", "success");
			
			retryCnt = 0;
			return newCoinObj;
		} catch (Exception e) {
			e.printStackTrace();
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCurrentCoin(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("OKEx Server Error : " + e.getMessage());
			}
		}
	}
}
