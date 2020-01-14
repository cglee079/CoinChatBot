package com.cglee079.coinchatbot.coin.pooler;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.coinchatbot.coin.HttpClient;
import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.exception.ServerErrorException;

public class BithumbPooler extends ApiPooler{
	private JSONObject coinObjs = null;
	private int istatus;
	private String errMessage;
	
	public BithumbPooler() throws ServerErrorException {
		getCoins();
	}
	
	public JSONObject getCoin(Coin coin) throws ServerErrorException {
		if(coinObjs != null) {
			String param = coinParam.get(coin);
			JSONObject coinObj =  coinObjs.getJSONObject(param);
			JSONObject newCoinObj = new JSONObject();
			newCoinObj.put("errorCode", istatus);
			newCoinObj.put("errorMsg", "");
			newCoinObj.put("result", "success");
			newCoinObj.put("volume", coinObj.getDouble("units_traded_24H"));
			newCoinObj.put("first", coinObj.getDouble("opening_price"));
			newCoinObj.put("last", coinObj.getDouble("closing_price"));
			newCoinObj.put("high", coinObj.getDouble("max_price"));
			newCoinObj.put("low", coinObj.getDouble("min_price"));
			
			return newCoinObj;
		} else {
			throw new ServerErrorException("Bithumb Server Error : " + errMessage);
		}
		
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() throws ServerErrorException{
		if(coinParam != null) {
			coinObjs = new JSONObject();
			
			String url = "https://api.bithumb.com/public/ticker/ALL";
			HttpClient httpClient = new HttpClient();
			String response;
			JSONObject responseObj = null;
			try {
				response = httpClient.get(url);
				responseObj = new JSONObject(response);
				
				String status  = responseObj.getString("status");
				istatus = Integer.parseInt(status);
				
				if(status.equals("0000")) {
					coinObjs = responseObj.getJSONObject("data");
				} else {
					String message = responseObj.getString("message");
					throw new Exception(message);
				}
				
				
			} catch (Exception e) {
				coinObjs 	= null;
				errMessage 	= e.getMessage();
			}
		}
	}
}