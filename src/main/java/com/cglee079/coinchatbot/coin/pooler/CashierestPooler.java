package com.cglee079.coinchatbot.coin.pooler;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.coinchatbot.coin.HttpClient;
import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.exception.ServerErrorException;
import com.google.gson.Gson;

public class CashierestPooler extends ApiPooler{
	private JSONObject coinObjs = null;
	private int istatus;
	private String errMessage;
	
	public CashierestPooler() throws ServerErrorException {
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
			newCoinObj.put("volume", coinObj.getDouble("baseVolume"));
			newCoinObj.put("first", coinObj.getDouble("last") / (( 100 + coinObj.getDouble("percentChange"))/100));
			newCoinObj.put("last", coinObj.getDouble("last"));
			newCoinObj.put("high", coinObj.getDouble("high24hr"));
			newCoinObj.put("low", coinObj.getDouble("low24hr"));
			
			return newCoinObj;
		} else {
			throw new ServerErrorException("Cashierest Server Error : " + errMessage);
		}
		
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() throws ServerErrorException{
		if(coinParam != null) {
			coinObjs = new JSONObject();
			
			String url = "https://api.cashierest.com/V2/PbV11/tickerall";
			HttpClient httpClient = new HttpClient();
			String response;
			JSONObject responseObj = null;
			try {
				response = httpClient.get(url);
				responseObj = new JSONObject(response.substring(response.indexOf("{"), response.length()));
				
				coinObjs = responseObj.getJSONObject("Cashierest");
				
			} catch (Exception e) {
				e.printStackTrace();
				coinObjs 	= null;
				errMessage 	= e.getMessage();
			}
		}
	}
	
	public static void main(String[] args) throws ServerErrorException {
		CashierestPooler a = new CashierestPooler();
		System.out.println(a.coinObjs);
	}
}