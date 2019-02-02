package com.cglee079.coinchatbot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Lang;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.config.id.MenuState;
import com.cglee079.coinchatbot.dao.ClientDao;
import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.ClientVo;
import com.cglee079.coinchatbot.util.TimeStamper;
import com.google.gson.Gson;

@Service
public class ClientService {
	
	@Value("#{constants['client.max.error.cnt']}")
	private int maxErrorCnt;
	
	@Autowired
	private ClientDao clientDao;

	public int count(Map<String, Object> map) {
		return clientDao.count(map);
	}
	
	public List<ClientVo> list(Map<String, Object> map) {
		if(map.containsKey("page") && map.containsKey("rows")) {
			int page = Integer.parseInt((String) map.get("page"));
			int rows = Integer.parseInt((String) map.get("rows"));
			int stRow = (page * rows) - rows;
			map.put("stRow", stRow);
		}

		return clientDao.list(map);
	}

	public List<ClientVo> list(Coin coinId, Market marketId, double coinValue, boolean isUp) {
		if(isUp) { return clientDao.listAlertTargetUp(coinId, marketId, coinValue); } 
		else { return clientDao.listAlertTargetDown(coinId, marketId, coinValue); }
	}
	
	public List<ClientVo> list(Coin coinId, Market marketId, Integer timeLoop, Integer dayLoop){
		return clientDao.listAlertLoop(coinId, marketId, timeLoop, dayLoop);
	}
	
	public List<ClientVo> listAtMidnight(Coin coinId, Market marketId, Integer timeLoop, int dayLoop, Date dateCurrent) {
		List<ClientVo> newclients = new ArrayList<>();
		List<ClientVo> clients = clientDao.listAlertLoop(coinId, marketId, timeLoop, dayLoop);
		ClientVo client = null;
		Date newDate = new Date();
		for(int i =0; i < clients.size(); i++) {
			client = clients.get(i);
			newDate.setTime(dateCurrent.getTime() + client.getLocaltime());
			if(newDate.getHours() == 0) {
				newclients.add(client);
			}
		}
		return newclients;
	}
	
	
	@Transactional	
	public boolean openChat(Coin coinId, Integer userId, String username, Market marketId) {
		
		ClientVo client = null;
		client = clientDao.get(coinId, userId.toString());

		if(client == null){
			client = ClientVo.builder()
					.coinId(coinId)
					.marketId(marketId)
					.userId(userId.toString())
					.username(username)
					.localtime((long)0)
					.lang(Lang.KR)
					.timeloop(3)
					.dayloop(1)
					.stateId(MenuState.MAIN)
					.openDate(TimeStamper.getDateTime())
					.errCnt(0)
					.enabled(true)
					.build();
			return clientDao.insert(client);
		} else{
			boolean enabled = client.isEnabled();
			if(enabled){
				return false;
			} else {
				client.setErrCnt(0);
				client.setEnabled(true);
				client.setReopenDate(TimeStamper.getDateTime());
				return clientDao.update(client);
			}
			}
	}

	@Transactional	
	public boolean stopChat(Coin coinId, int userId) {
		ClientVo client = clientDao.get(coinId, String.valueOf(userId));
		if(client != null) {
			client.setTargetUp(null);
			client.setTargetDown(null);
			client.setTimeloop(0);
			client.setDayloop(0);
			return clientDao.update(client);
		}
		return false;
	}
	
	@Transactional	
	public boolean increaseErrCnt(Coin coinId, String userId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			if(client.isEnabled()){
				int errCnt = client.getErrCnt();
				errCnt = errCnt + 1;
				if(errCnt > maxErrorCnt) {
					Log.i("Close Client\t:\t[id :" + userId+ "\t" + client.getUsername() + " ] ");
					client.setEnabled(false);
					client.setCloseDate(TimeStamper.getDateTime());
				} else {
					client.setErrCnt(errCnt);
				}
				return clientDao.update(client);
			} else{
				return false;
			}
		} else{
			return false;
		}
		
	}
	
	public boolean delete(ClientVo client) {
		return clientDao.delete(client);
	}
	
	public boolean update(ClientVo client) {
		return clientDao.update(client);
	}
		
	@Transactional	
	public boolean updateByAdmin(ClientVo client) {
		ClientVo saved  = clientDao.get(client.getCoinId(), client.getUserId());

		saved.setMarketId(client.getMarketId());
		saved.setLang(client.getLang());
		saved.setStateId(client.getStateId());
		saved.setDayloop(client.getDayloop());
		saved.setTimeloop(client.getTimeloop());
		saved.setErrCnt(client.getErrCnt());
		saved.setEnabled(client.isEnabled());
		
		return clientDao.update(saved);
		
	}
	
	@Transactional	
	public boolean updateState(Coin coinId, String userId, MenuState stateId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setStateId(stateId);
			return clientDao.update(client);
		} else{
			return false;
		}
		
	}

	@Transactional	
	public boolean updateMarketId(Coin coinId, String userId, Market marketId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setMarketId(marketId);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	@Transactional	
	public boolean updatePrice(Coin coinId, String userId, Double price) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setInvest(price);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	@Transactional	
	public boolean updateTargetUpPrice(Coin coinId, String userId, Double targetPrice) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUp(targetPrice);
			client.setTargetDown(0.0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	@Transactional	
	public boolean updateTargetDownPrice(Coin coinId, String userId, Double targetPrice) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUp(0.0);
			client.setTargetDown(targetPrice);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	@Transactional	
	public boolean clearTargetPrice(Coin coinId, String userId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUp(0.0);
			client.setTargetDown(0.0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	@Transactional	
	public boolean updateTimeLoop(Coin coinId, String userId, int timeloop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTimeloop(timeloop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	@Transactional	
	public boolean updateDayLoop(Coin coinId, String userId, int dayLoop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setDayloop(dayLoop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	@Transactional	
	public boolean updateNumber(Coin coinId, String userId, double number) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setCoinCnt(number);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	@Transactional	
	public boolean updateLocalTime(Coin coinId, String userId, Long localTime) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setLocaltime(localTime);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	@Transactional	
	public boolean updateLanguage(Coin coinId, String userId, Lang lang) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setLang(lang);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateMsgDate(ClientVo client) {
		if(client != null){
			client.setLaMsgDate(TimeStamper.getDateTime());
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public ClientVo get(Coin coinId, String userId) {
		return clientDao.get(coinId, userId);
	}
	
	public ClientVo get(Coin coinId, int userId) {
		return this.get(coinId, String.valueOf(userId));
	}
	
	/* 통계 관련 카운트 */
	public Object countChat(Boolean enabled) {
		return clientDao.countChat(enabled); 
	}

	public Object countUser(Boolean enabled) {
		return clientDao.countUser(enabled); 
	}

	public JSONArray countNewClientInToday() {
		List<Map<String, Object>> clients = clientDao.countNewClientInToday();
		return new JSONArray(new Gson().toJson(clients));
	}

	public JSONArray countNewClientInTomonth() {
		List<Map<String, Object>> clients = clientDao.countNewClientInTomonth();
		return new JSONArray(new Gson().toJson(clients));
	}
	


}
