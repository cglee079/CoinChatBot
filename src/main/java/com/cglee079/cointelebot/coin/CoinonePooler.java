package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class CoinonePooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_BTC : param = "btc"; break;
		case ID.COIN_XRP : param = "xrp"; break;
		case ID.COIN_ETH : param = "eth"; break;
		case ID.COIN_QTM : param = "qtum"; break;
		case ID.COIN_LTC : param = "ltc"; break;
		case ID.COIN_BCH : param = "bch"; break;
		case ID.COIN_ETC : param = "etc"; break;
		}
		
		String url = "https://api.coinone.co.kr/ticker/?format=json&currency=" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj = new JSONObject(response);
			if(jsonObj.getString("result").equals("success")){ 
				jsonObj.put("errorMsg", "");
				
				retryCnt = 0;
				return jsonObj;				
			} else {
				throw new ServerErrorException("코인원 서버 에러", jsonObj.getInt("errorCode"));
			}
		} catch (IOException e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("코인원 서버 에러 : " + e.getMessage());
			}
		}
		
	}
}
