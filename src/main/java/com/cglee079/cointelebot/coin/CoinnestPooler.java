package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;

public class CoinnestPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_BTC : param = "btc"; break;
		case ID.COIN_BCH : param = "bch"; break;
		case ID.COIN_ETH : param = "eth"; break;
		case ID.COIN_ETC : param = "etc"; break;
		case ID.COIN_QTM : param = "qtum"; break;
		case ID.COIN_LTC : param = "ltc"; break;
		case ID.COIN_NEO : param = "neo"; break;
		case ID.COIN_ADA : param = "ada"; break;
		case ID.COIN_TRX : param = "tron"; break;
		}
		
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
			coinObj.put("volume", data.getLong("vol"));
			coinObj.put("last", data.getDouble("last"));
			coinObj.put("high", data.getDouble("high"));
			coinObj.put("low", data.getDouble("low"));
			
			retryCnt = 0;
			return coinObj;				
		} catch (IOException e) {
			Log.i("ERROR 코인네스트 서버 에러 : " + e.getMessage());
			Log.i(e.getStackTrace());
			
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("코인네스트 서버 에러 : " + e.getMessage());
			}
		}
	}
}