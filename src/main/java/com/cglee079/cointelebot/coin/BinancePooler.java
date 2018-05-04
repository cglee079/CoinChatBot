package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class BinancePooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_STM : param = "STORMBTC"; break;
		case ID.COIN_GTO : param = "GTOBTC"; break;
		case ID.COIN_KNC : param = "KNCBTC"; break;
		}
		
		JSONObject coinObj = getCurrentCoin(param);
		JSONObject btcObj = getCurrentCoin("BTCUSDT");
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("last", coinObj.getDouble("lastPrice") * btcObj.getDouble("lastPrice"));
		return resultObj;
	}
	
	public JSONObject getCurrentCoin(String param) throws ServerErrorException {
		String url = "https://api.binance.com/api/v1/ticker/24hr";
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONArray jsonArr= new JSONArray(response);
			JSONObject jsonObj = null; 
			for(int i = 0; i < jsonArr.length(); i++) {
				jsonObj = jsonArr.getJSONObject(i);
				if(jsonObj.getString("symbol").equals(param)) {
					break;
				}
			}

			retryCnt = 0;
			return jsonObj;
		} catch (IOException e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCurrentCoin(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("바이낸스 서버 에러 : " + e.getMessage());
			}
		}
	}
}
