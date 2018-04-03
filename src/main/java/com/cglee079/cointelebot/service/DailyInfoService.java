package com.cglee079.cointelebot.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.DailyInfoDao;
import com.cglee079.cointelebot.model.DailyInfoVo;
import com.google.gson.Gson;

@Service
public class DailyInfoService {

	@Autowired
	private DailyInfoDao dailyInfoDao;

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public DailyInfoVo get(Date d, String exchange) {
		return dailyInfoDao.get(formatter.format(d), exchange);
	}

	public boolean insert(Date d, String exchange, JSONObject coin) {
		DailyInfoVo dailyInfo = new DailyInfoVo();
		dailyInfo.setDate(formatter.format(d));
		dailyInfo.setExchange(exchange);
		dailyInfo.setHigh(coin.getDouble("high"));
		dailyInfo.setLow(coin.getDouble("low"));
		dailyInfo.setLast(coin.getDouble("last"));
		dailyInfo.setVolume((long) coin.getDouble("volume"));
		dailyInfo.setResult(coin.getString("result"));
		dailyInfo.setErrorCode(String.valueOf(coin.getInt("errorCode")));
		dailyInfo.setErrorMsg(coin.getString("errorMsg"));
		return dailyInfoDao.insert(dailyInfo);
	}

	public JSONObject getBefore(Date dateCurrent, String exchange) {
		Date dateBefore = new Date();
		dateBefore.setTime(dateCurrent.getTime() - (1000 * 60 * 60 * 24));
		DailyInfoVo dailyInfo = dailyInfoDao.get(formatter.format(dateBefore), exchange);
		Gson gson = new Gson();
		String jsonString = gson.toJson(dailyInfo);
		return new JSONObject(jsonString);
	}

}
