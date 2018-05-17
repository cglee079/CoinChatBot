package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;

public class PoloniexPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_XLM : param = "USDT_STR"; break;
		}
		
		String url = "https://poloniex.com/public?command=returnTicker";
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj= new JSONObject(response).getJSONObject(param);
			JSONObject coinObj = new JSONObject();
			coinObj.put("last", jsonObj.get("last"));
			
			retryCnt = 0;
			return coinObj;
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("폴로닉스 서버 에러 : " + e.getMessage());
			}
		}
	}
}
