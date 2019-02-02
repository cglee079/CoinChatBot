package com.cglee079.coinchatbot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.model.ClientVo;
import com.cglee079.coinchatbot.telegram.TelegramNotifier;
import com.google.gson.Gson;

@Service
public class PostMessageService {
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private TelegramNotifier telegramNotifier;
	
	public Map<String, Object> view(Map<String, Object> map, String caseId) {
		JSONObject data = new JSONObject();
		JSONArray rows = new JSONArray();
		int count = 0;
		
		switch(caseId) {
		case "all" :
			List<ClientVo> clients = clientService.list(map);
			
			rows = new JSONArray(new Gson().toJson(clients));
			count = clientService.count(map);
			
			data = new JSONObject();
			data.put("rows", rows);
			data.put("total", count);
			break;
		case "one": 
			count = 1;
			
			JSONObject one = new JSONObject();
			
			one.put("coinId", (String)map.get("coinId"));
			one.put("userId", (String)map.get("userId"));
			
			rows.put(one);
			
			data.put("rows", rows);
			data.put("total", 1);
			break;
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("data", data.toString());
		result.put("count", count);
		
		return result;
		
	}

	public JSONObject post(Map<String, Object> map) {
		Coin coinId 	= Coin.valueOf((String)map.get("coinId"));
		String userId 	= (String)map.get("userId");
		String content 	= (String)map.get("content");
		String response	= null;
		
		try {
			telegramNotifier.sendMessage(coinId, userId, content);
			response = "SUCCESS\t: " + coinId + "\t[" + userId + " " + "]\t:\t" + content.replace("\n", " ");
		} catch (Exception e) {
			e.printStackTrace();
			response = "ERROR\t: " + coinId + "\t[" + userId + " " + "]\t:\t " + "  Message : " + e.getMessage();
		}
		
		JSONObject result = new JSONObject();
		result.put("coinId", coinId);
		result.put("userId", userId);
		result.put("response", response);
		
		return result;
	}
	
}
