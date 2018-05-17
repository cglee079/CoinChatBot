package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;

public class KorbitPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_BTC : param = "btc_krw"; break;
		case ID.COIN_BCH : param = "bch_krw"; break;
		case ID.COIN_ETH : param = "eth_krw"; break;
		case ID.COIN_ETC : param = "etc_krw"; break;
		case ID.COIN_XRP : param = "xrp_krw"; break;
		}
		
		String url = "https://api.korbit.co.kr/v1/ticker/detailed?currency_pair=" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject coinObj = new JSONObject();
			JSONObject data = new JSONObject(response);
			coinObj.put("errorCode", 0);
			coinObj.put("errorMsg", "");
			coinObj.put("result", "success");
			coinObj.put("volume", data.getDouble("volume"));
			coinObj.put("last", data.getDouble("last"));
			coinObj.put("high", data.getDouble("high"));
			coinObj.put("low", data.getDouble("low"));
			
			retryCnt = 0;
			return coinObj;				
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("코빗 서버 에러 : " + e.getMessage());
			}
		}
	}
}