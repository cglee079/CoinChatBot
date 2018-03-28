package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class BittrexPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_ADA : param = "USDT-ADA"; break;
		case ID.COIN_XVG : param = "USDT-XVG"; break;
		}
		
		String url = "https://bittrex.com/api/v1.1/public/getticker?market=" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj= new JSONObject(response);
			JSONObject coinObj = new JSONObject();
			coinObj.put("bid", jsonObj.getJSONObject("result").get("Bid"));
			coinObj.put("ask", jsonObj.getJSONObject("result").get("Ask"));
			coinObj.put("last", jsonObj.getJSONObject("result").get("Last"));
			
			retryCnt = 0;
			return coinObj;
		} catch (IOException e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("피트트렉스 서버 에러 : " + e.getMessage());
			}
		}
	}
}
