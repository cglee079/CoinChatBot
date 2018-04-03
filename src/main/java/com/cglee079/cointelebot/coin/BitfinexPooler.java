package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class BitfinexPooler extends ApiPooler{
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_BTC : param = "tBTCUSD"; break;
		case ID.COIN_XRP : param = "tXRPUSD"; break;
		case ID.COIN_ETH : param = "tETHUSD"; break;
		case ID.COIN_EOS : param = "tEOSUSD"; break;
		case ID.COIN_QTM : param = "tQTMUSD"; break;
		case ID.COIN_LTC : param = "tLTCUSD"; break;
		case ID.COIN_BCH : param = "tBCHUSD"; break;
		case ID.COIN_ETC : param = "tETCUSD"; break;
		case ID.COIN_NEO : param = "tNEOUSD"; break;
		case ID.COIN_TRX : param = "tTRXUSD"; break;
		}
		
		String url = "https://api.bitfinex.com/v2/ticker/" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONArray jsonArr= new JSONArray(response);
			JSONObject coinObj = new JSONObject();
			coinObj.put("bid", jsonArr.get(0));
			coinObj.put("bid_size", jsonArr.get(1));
			coinObj.put("ask", jsonArr.get(2));
			coinObj.put("ask_size", jsonArr.get(3));
			//coinObj.put("bid", jsonArr.get(4));
			//coinObj.put("bid", jsonArr.get(5));
			coinObj.put("last", jsonArr.get(6));
			coinObj.put("volume", jsonArr.get(7));
			coinObj.put("high", jsonArr.get(8));
			coinObj.put("low", jsonArr.get(9));
			
			retryCnt = 0;
			return coinObj;
		} catch (IOException e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("피트파이넥스 서버 에러 : " + e.getMessage());
			}
		}
	}
	
}
