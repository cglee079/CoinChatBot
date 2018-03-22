package com.cglee079.cointelebot.coin;

import java.io.IOException;

import org.json.JSONObject;

import com.cglee079.cointelebot.constants.C;
import com.cglee079.cointelebot.exception.ServerErrorException;

public class BithumbPooler extends ApiPooler{
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = "";
		switch (coin) {
		case C.COIN_BTC : param = "btc"; break;
		case C.COIN_XRP : param = "xrp"; break;
		case C.COIN_ETH : param = "eth"; break;
		case C.COIN_EOS : param = "eos"; break;
		case C.COIN_QTM : param = "qtum"; break;
		case C.COIN_LTC : param = "ltc"; break;
		case C.COIN_BCH : param = "bch"; break;
		case C.COIN_ETC : param = "etc"; break;
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
				coinObj.put("first", (int)data.getDouble("opening_price"));
				coinObj.put("last", (int)data.getDouble("closing_price"));
				coinObj.put("high", (int)data.getDouble("max_price"));
				coinObj.put("low", (int)data.getDouble("min_price"));
				
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