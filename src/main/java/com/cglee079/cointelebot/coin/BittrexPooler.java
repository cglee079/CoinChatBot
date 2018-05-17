package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class BittrexPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_BTC : param = "USDT-BTC"; break;
		case ID.COIN_ADA : param = "USDT-ADA"; break;
		case ID.COIN_XVG : param = "USDT-XVG"; break;
		case ID.COIN_NXT : param = "USDT-NXT"; break;
		case ID.COIN_OMG : param = "USDT-OMG"; break;
		case ID.COIN_EMC : param = "BTC-EMC2"; break;
		case ID.COIN_ARD : param = "BTC-ARDR"; break;
		case ID.COIN_GRS : param = "BTC-GRS"; break;
		case ID.COIN_SIA : param = "BTC-SC"; break;
		}

		JSONObject coinObj = getCurrentCoin(param);
		
		if(param.contains("BTC-")) {
			JSONObject btcObj = getCurrentCoin("USDT-BTC");
			coinObj.put("last", coinObj.getDouble("last") * btcObj.getDouble("last"));
			
		}
		

		return coinObj;
	}
	
	public JSONObject getCurrentCoin(String param) throws ServerErrorException {
		String url = "https://bittrex.com/api/v1.1/public/getticker?market=" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj= new JSONObject(response);
			JSONObject coinObj = new JSONObject();
			coinObj.put("bid", jsonObj.getJSONObject("result").getDouble("Bid"));
			coinObj.put("ask", jsonObj.getJSONObject("result").getDouble("Ask"));
			coinObj.put("last", jsonObj.getJSONObject("result").getDouble("Last"));
			
			retryCnt = 0;
			return coinObj;
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCurrentCoin(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("피트트렉스 서버 에러 : " + e.getMessage());
			}
		}
	}
}
