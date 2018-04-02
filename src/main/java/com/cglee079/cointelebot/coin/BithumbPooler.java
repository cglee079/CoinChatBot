package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class BithumbPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case ID.COIN_BTC : param = "btc"; break;
		case ID.COIN_XRP : param = "xrp"; break;
		case ID.COIN_ETH : param = "eth"; break;
		case ID.COIN_EOS : param = "eos"; break;
		case ID.COIN_QTM : param = "qtum"; break;
		case ID.COIN_LTC : param = "ltc"; break;
		case ID.COIN_BCH : param = "bch"; break;
		case ID.COIN_ETC : param = "etc"; break;
		}
		
		String url = "https://api.bithumb.com/public/ticker/" + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj = new JSONObject(response);
			if(jsonObj.getInt("status") == 0000){
				JSONObject coinObj = new JSONObject();
				JSONObject data = jsonObj.getJSONObject("data");
				coinObj.put("errorCode", jsonObj.getInt("status"));
				coinObj.put("errorMsg", "");
				coinObj.put("result", "success");
				coinObj.put("volume", data.getDouble("volume_1day"));
				coinObj.put("first", data.getDouble("opening_price"));
				coinObj.put("last", data.getDouble("closing_price"));
				coinObj.put("high", data.getDouble("max_price"));
				coinObj.put("low", data.getDouble("min_price"));
				
				retryCnt = 0;
				return coinObj;				
			} else{
				throw new ServerErrorException("빗썸 서버 에러", jsonObj.getInt("status"));
			}
		} catch (IOException e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCoin(coin);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("빗썸 서버 에러 : " + e.getMessage());
			}
		}
	}
}