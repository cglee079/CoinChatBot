package com.cglee079.cointelebot.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.dao.TimelyInfoDao;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.TimelyInfoVo;
import com.google.gson.Gson;

@Service
public class TimelyInfoService {

	@Autowired
	private TimelyInfoDao timelyInfoDao;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");

	public TimelyInfoVo get(Date d, String market) {
		return timelyInfoDao.get(formatter.format(d), market);
	}

	public boolean insert(Date d, String market, JSONObject coin) {
		TimelyInfoVo timelyInfo = new TimelyInfoVo();
		timelyInfo.setDate(formatter.format(d));
		timelyInfo.setMarket(market);
		timelyInfo.setHigh(coin.getDouble("high"));
		timelyInfo.setLow(coin.getDouble("low"));
		timelyInfo.setLast(coin.getDouble("last"));
		timelyInfo.setVolume((long) coin.getDouble("volume"));
		timelyInfo.setResult(coin.getString("result"));
		timelyInfo.setErrorCode(String.valueOf(coin.getInt("errorCode")));
		timelyInfo.setErrorMsg(coin.getString("errorMsg"));
		return timelyInfoDao.insert(timelyInfo);
	}

	public JSONObject getBefore(Date dateCurrent, String market) {
		Date dateBefore = new Date();
		dateBefore.setTime(dateCurrent.getTime() - (1000 * 60 * 60));
		TimelyInfoVo timelyInfo = timelyInfoDao.get(formatter.format(dateBefore), market);
		Gson gson = new Gson();
		String jsonString = gson.toJson(timelyInfo);
		return new JSONObject(jsonString);
	}

	public List<JSONObject> list(String market, int cnt) {
		List<TimelyInfoVo> timelyInfos = timelyInfoDao.list(market, cnt);
		List<JSONObject> jsons = new ArrayList<>();
		String jsonString = null; 
		Gson gson = new Gson();
		
		for(int i = 0; i < timelyInfos.size(); i++) {
			jsonString = gson.toJson(timelyInfos.get(i));
			jsons.add(jsons.size(), new JSONObject(jsonString));
		}
		return jsons;
	}

}
