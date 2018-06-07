package com.cglee079.cointelebot.coin;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.exception.ServerErrorException;

public class BitfinexPooler extends ApiPooler{
	private JSONObject coinObjs = null;
	private String errMessage;
	
	public BitfinexPooler() throws ServerErrorException {
		getCoins();
	}
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		if(coinObjs != null) {
			return coinObjs.getJSONObject(coinParam.get(coin));
		} else {
			throw new ServerErrorException("Bifinex Server Error : " + errMessage);
		}
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() throws ServerErrorException{
		String param = "";
		if(coinParam != null) {
			coinObjs = new JSONObject();
			
			Iterator<String> iter = coinParam.keySet().iterator();
			while(iter.hasNext()) {
				param += coinParam.get(iter.next()) + ",";
			}
			
			String url = "https://api.bitfinex.com/v2/tickers?symbols=" + param;
			HttpClient httpClient = new HttpClient();
			String response;
			try {
				response = httpClient.get(url);
				JSONArray jsonArr	= new JSONArray(response);
				JSONArray coinArr 	= null;
				JSONObject coinObj 	= null;
				String coinParam 	= null;
				
				for(int i = 0; i < jsonArr.length(); i++) {
					coinArr = jsonArr.getJSONArray(i);
					coinObj = new JSONObject();
					coinParam =  coinArr.getString(0);
					coinObj.put("first", coinArr.getDouble(7) / ( 1 + coinArr.getDouble(6)));
					coinObj.put("last", coinArr.getDouble(7));
					coinObj.put("volume", coinArr.getDouble(8));
					coinObj.put("high", coinArr.getDouble(9));
					coinObj.put("low", coinArr.getDouble(10));
					coinObj.put("errorCode", 0);
					coinObj.put("errorMsg", "");
					coinObj.put("result", "success");
					
					coinObjs.put(coinParam, coinObj);
				}
				
			} catch (Exception e) {
				coinObjs 	= null;
				errMessage 	= e.getMessage();
			}
		}
	}
	
}
