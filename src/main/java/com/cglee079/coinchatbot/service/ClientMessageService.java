package com.cglee079.coinchatbot.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.log.MyLog;
import com.cglee079.coinchatbot.dao.ClientMessageDao;
import com.cglee079.coinchatbot.model.ClientMessageVo;
import com.cglee079.coinchatbot.util.TimeStamper;

@Service
public class ClientMessageService {
	
	@Autowired
	private ClientMessageDao clientMessageDao;

	@Value("#{telegram['my.id']}")
	private String myId; 
	
	public int count(Map<String, Object> map) {
		return clientMessageDao.count(map);
	}
	
	public List<ClientMessageVo> list(Map<String, Object> map) {
		int page = Integer.parseInt((String) map.get("page"));
		int rows = Integer.parseInt((String) map.get("rows"));
		int stRow = (page * rows) - rows;
		map.put("stRow", stRow);
		return clientMessageDao.list(map);
	}
	
	public boolean insert(Coin coinId, Update update) {
		Message message = null;
		if(update.getMessage() != null) {
			message = update.getMessage();
		} else if( update.getEditedMessage() != null) {
			message = update.getEditedMessage();
		}
		
		User user = message.getFrom();
		
		ClientMessageVo clientMsg = ClientMessageVo.builder()
				.coinId(coinId)
				.userId(user.getId().toString())
				.username(user.getLastName() + " " + user.getFirstName())
				.contents(message.getText())
				.date(TimeStamper.getDateTime())
				.build();
		
		MyLog.i(this.getClass(), clientMsg.log(coinId));
		
		return clientMessageDao.insert(clientMsg);
	}
	

	public int deleteMyMessage() {
		return clientMessageDao.delete(myId);
	}
	
	/** 통계 관련 **/
	public Map<String,Object> countTotalMessage() {
		List<Map<String, Object>> msgCnts = clientMessageDao.countTotalMessage();
		Map<String, Object> row = null;
		
		Map<String, Integer> msgCntByDay 		= new HashMap<String, Integer>();
		Map<String, Integer> msgCntByMonth 		= new HashMap<String, Integer>();
		Map<String, Integer> msgUserCntByDay 	= new HashMap<String, Integer>();
		Map<Coin, Integer> coinsMap = new HashMap<Coin, Integer>();
		Coin coinId 	= null;
		String month 	= null;
		String monthKey	= null;
		String day 		= null;
		String dayKey	= null;
		int totalMsgCnt = 0;
		int msgCnt 		= 0;
		int userCnt 	= 0;
		for(int i = 0; i < msgCnts.size(); i++) {
			row = msgCnts.get(i);
			coinId 		= Coin.valueOf((String)row.get("coinId"));
			month		= (String)row.get("month");
			day 		= (String)row.get("day");
			monthKey 	= month + "_"  + coinId;
			dayKey 		= day + "_"  + coinId;
			msgCnt 		= ((Long)row.get("msgCnt")).intValue();
			userCnt		= ((Long)row.get("userCnt")).intValue();
			totalMsgCnt	= totalMsgCnt + msgCnt;
			
			coinsMap.put(coinId, 0);
			
			if(msgCntByDay.containsKey(dayKey)) { msgCntByDay.put(dayKey, msgCntByDay.get(dayKey) + msgCnt);} 
			else { msgCntByDay.put(dayKey, msgCnt); }
			
			if(msgCntByMonth.containsKey(monthKey)) { msgCntByMonth.put(monthKey, msgCntByMonth.get(monthKey) + msgCnt);}
			else { msgCntByMonth.put(monthKey, msgCnt); }
			
			if(msgUserCntByDay.containsKey(dayKey)) { msgUserCntByDay.put(dayKey, msgUserCntByDay.get(dayKey) + userCnt); } 
			else { msgUserCntByDay.put(dayKey, userCnt); }
			
		}
		
		Iterator<Coin> iter =  null;
		
		JSONArray coins = new JSONArray();
		iter = coinsMap.keySet().iterator();
		while(iter.hasNext()) {
			coins.put(iter.next().name());
		}
		
		////////////
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalMsgCnt", totalMsgCnt);
		result.put("msgCntByDay", mapToJsonArray(coins,  msgCntByDay));
		result.put("msgCntByMonth", mapToJsonArray(coins,  msgCntByMonth));
		result.put("msgUserCntByDay", mapToJsonArray(coins,  msgUserCntByDay));
		result.put("coins", coins);
		
		return result;
	}
	
	private JSONArray mapToJsonArray(JSONArray coins, Map<String, Integer> msgCntByDay) {
		JSONArray msgCntByDayArr = new JSONArray();
		JSONObject obj 	= null;
		
		String[] split 	= null;
		String day 		= null;
		String coin 	= null;
		Iterator<String> iter = msgCntByDay.keySet().iterator();
		String key  = null;
		while(iter.hasNext()) {
			key 	= iter.next();
			split 	= key.split("_");
			day 	= split[0].trim();
			coin 	= split[1].trim();
			obj		= null;
			
			for(int i = 0; i < msgCntByDayArr.length(); i++) {
				if(msgCntByDayArr.getJSONObject(i).getString("date").equals(day)) {
					obj = msgCntByDayArr.getJSONObject(i);
					break;
				} 
			}
			
			if(obj != null) {
				obj.put("total", obj.getInt("total") + msgCntByDay.get(key));
				obj.put(coin, obj.getInt(coin) + msgCntByDay.get(key));
			} else {
				obj = new JSONObject();
				obj.put("date", day);
				obj.put("total", 0);
				for(int i = 0; i < coins.length(); i++) {
					obj.put(coins.getString(i), 0);
				}
				//
				obj.put("total", obj.getInt("total") + msgCntByDay.get(key));
				obj.put(coin, obj.getInt(coin) + msgCntByDay.get(key));
				msgCntByDayArr.put(obj);
			}
			
			
			
		}
		
		return sortJsonArr(msgCntByDayArr, "date");
	}
	
	private JSONArray sortJsonArr(JSONArray jsonArr, final String key) {
	    JSONArray sortedJsonArray = new JSONArray();

	    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < jsonArr.length(); i++) {
	        jsonValues.add(jsonArr.getJSONObject(i));
	    }
	    Collections.sort( jsonValues, new Comparator<JSONObject>() {
	        @Override
	        public int compare(JSONObject a, JSONObject b) {
	            String valA = new String();
	            String valB = new String();

	            try {
	                valA = (String) a.get(key);
	                valB = (String) b.get(key);
	            } 
	            catch (JSONException e) {
	                //do something
	            }

	            return valA.compareTo(valB) * -1;
	        }
	    });

	    for (int i = 0; i < jsonArr.length(); i++) {
	        sortedJsonArray.put(jsonValues.get(i));
	    }
	    
	    return sortedJsonArray;
	}

}
