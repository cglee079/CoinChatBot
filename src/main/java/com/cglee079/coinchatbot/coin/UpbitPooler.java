package com.cglee079.coinchatbot.coin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cglee079.coinchatbot.exception.ServerErrorException;

public class UpbitPooler extends ApiPooler{
	protected static final int MAX_RETRY_CNT = 5;
	protected int retryCnt = 0;
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		JSONObject coinObj = getCurrentCoin(param);
		JSONObject onedayInfo = getOnedayInfo(param);
		coinObj.put("high", onedayInfo.get("high"));
		coinObj.put("low", onedayInfo.get("low"));
		coinObj.put("volume", onedayInfo.get("volume"));
		coinObj.put("first", this.getFirst(param).get("first"));		
		
		retryCnt = 0;
		return coinObj;
	}
	
	public JSONObject getCurrentCoin(String param) throws ServerErrorException {
		String url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT." + param;
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj = new JSONArray(response).getJSONObject(0);
			JSONObject coinObj = new JSONObject();
			coinObj.put("errorMsg", "");
			coinObj.put("errorCode", "0");
			coinObj.put("result", "success");
			coinObj.put("last", jsonObj.getDouble("tradePrice"));
			return coinObj;				
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getCurrentCoin(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("Upbit Server Error : " + e.getMessage());
			}
		}
	}
	
	public JSONObject getFirst(String param) throws ServerErrorException {
		String url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT." + param;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
		Date dateBefore = new Date();
		dateBefore.setTime(dateBefore.getTime() - (1 * 24 * 60 * 60 * 1000) - (9 * 60 * 60 * 1000)); // 1일전, UTC
		String to = formatter.format(dateBefore);
		
		url += "&to=";
		url += to;
		
		HttpClient httpClient = new HttpClient();
		String response;
		try {
			response = httpClient.get(url);
			JSONObject jsonObj = new JSONArray(response).getJSONObject(0);
			JSONObject info = new JSONObject();
			info.put("first", jsonObj.getDouble("tradePrice"));
			
			return info;				
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getFirst(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("Upbit Server Error : " + e.getMessage());
			}
		}
	}
	
	public JSONObject getOnedayInfo(String param) throws ServerErrorException {
		ArrayList<Double> maxs = new ArrayList<Double>();
		ArrayList<Double> mins = new ArrayList<Double>();
		double volume = 0;

		HttpClient httpClient = new HttpClient();
		String url = null;
		String response = null;
		JSONArray jsonArr = null;
		JSONObject jsonObj = null;
		int size = -1;
		
		
		//get hours
		url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/60?code=CRIX.UPBIT." + param + "&count=24";
		
		try {
			response = httpClient.get(url);
			jsonArr = new JSONArray(response);
			jsonObj = null;
			
			size = jsonArr.length();
			for(int i = 0; i < size; i++) {
				jsonObj = jsonArr.getJSONObject(i);
				maxs.add(jsonObj.getDouble("highPrice"));
				mins.add(jsonObj.getDouble("lowPrice"));
				volume += jsonObj.getDouble("candleAccTradeVolume");
			}
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getOnedayInfo(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("Upbit Server Error : " + e.getMessage());
			}
		}
		
		//get minutes
		url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT." + param;
		Date d = new Date();
		int hour = d.getHours();
		int minute = d.getMinutes();
		int count = 0;
		
		if(minute == 0) { count = 0;}
		else { 
			hour = hour + 1;
			count = 60 - minute; 
		}
		
		d.setTime(d.getTime() - (1 * 24 * 60 * 60 * 1000)); // 1일전
		d.setHours(hour);
		d.setMinutes(0);
		d.setTime(d.getTime() - (9 * 60 * 60 * 1000)); //UTD
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
		String to = formatter.format(d);
		
		url += "&to=";
		url += to;
		url += "&count=";
		url += count;
		
		try {
			response = httpClient.get(url);
			jsonArr = new JSONArray(response);
			jsonObj = null;
			
			size = jsonArr.length();
			for(int i = 0; i < size; i++) {
				jsonObj = jsonArr.getJSONObject(i);
				maxs.add(jsonObj.getDouble("highPrice"));
				mins.add(jsonObj.getDouble("lowPrice"));
				volume += jsonObj.getDouble("candleAccTradeVolume");
			}
		} catch (Exception e) {
			retryCnt++;
			if(retryCnt < MAX_RETRY_CNT) {
				return this.getOnedayInfo(param);
			} else {
				retryCnt = 0;
				throw new ServerErrorException("Upbit Server Error : " + e.getMessage());
			}
		}
		
		
		JSONObject oneDayInfo = new JSONObject();
		oneDayInfo.put("high", Collections.max(maxs));
		oneDayInfo.put("low", Collections.min(mins));
		oneDayInfo.put("volume", volume);
		return oneDayInfo;
	}
}