package com.cglee079.coinchatbot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.constants.ID;
import com.cglee079.coinchatbot.constants.SET;
import com.cglee079.coinchatbot.dao.ClientDao;
import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.ClientVo;
import com.cglee079.coinchatbot.util.TimeStamper;

@Service
public class ClientService {
	
	@Autowired
	private ClientDao clientDao;

	public List<ClientVo> list(String coinId) {
		return clientDao.list(coinId);
	}
	
	public List<ClientVo> list(String coinId, String market, double coinValue, boolean isUp) {
		if(isUp) { return clientDao.listTargetUp(coinId, market, coinValue); } 
		else { return clientDao.listTargetDown(coinId, market, coinValue); }
	}
	
	public List<ClientVo> list(String coinId, String market, Integer timeLoop, Integer dayLoop){
		return clientDao.list(coinId, market, timeLoop, dayLoop);
	}
	
	public List<ClientVo> listAtMidnight(String coinId, String market, Integer timeLoop, int dayLoop, Date dateCurrent) {
		List<ClientVo> newclients = new ArrayList<>();
		List<ClientVo> clients = clientDao.list(coinId, market, timeLoop, dayLoop);
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
	
	public String getState(String coinId, Integer id) {
		ClientVo client = clientDao.get(coinId, id.toString());
		if(client != null) {
			return client.getState();
		} else {
			return null;
		}
	}
	
	public String getMarketId(String coinId, String userId){
		ClientVo client = clientDao.get(coinId, userId);
		return client.getMarketId();
	}
	
	public String getMarket(String coinId, long userId) {
		return this.getMarketId(coinId, String.valueOf(userId));
	}
	
	public boolean openChat(String coinId, Integer userId, String username, String market) {
		
		ClientVo client = null;
		client = clientDao.get(coinId, userId.toString());

		if(client == null){
			client = ClientVo.builder()
					.coinId(coinId)
					.marketId(market)
					.userId(userId.toString())
					.username(username)
					.localtime((long)0)
					.lang(ID.LANG_KR)
					.timeloop(3)
					.dayloop(1)
					.state(ID.STATE_MAIN)
					.openDate(TimeStamper.getDateTime())
					.errCnt(0)
					.enabled("Y")
					.build();
			return clientDao.insert(client);
		} else{
			String enabled = client.getEnabled();
			if(enabled.equals("Y")){
				return false;
			}
			else if(enabled.equals("N")){
				client.setErrCnt(0);
				client.setEnabled("Y");
				client.setReopenDate(TimeStamper.getDateTime());
				return clientDao.update(client);
			}
			else{
				return false;
			}
		}
	}

	public boolean stopChat(String coinId, int userId) {
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
	
	public boolean increaseErrCnt(String coinId, String userId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			if(client.getEnabled().equals("Y")){
				int errCnt = client.getErrCnt();
				errCnt = errCnt + 1;
				if(errCnt > SET.CLNT_MAX_ERRCNT) {
					Log.i("Close Client\t:\t[id :" + userId+ "\t" + client.getUsername() + " ] ");
					client.setEnabled("N");
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
	
	public boolean update(ClientVo client) {
		return clientDao.update(client);
	}
	
	public boolean updateState(String coinId, String userId, String state) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setState(state);
			return clientDao.update(client);
		} else{
			return false;
		}
		
	}

	public boolean updateMarket(String coinId, String userId, String market) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setMarketId(market);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updatePrice(String coinId, String userId, Double price) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setInvest(price);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTargetUpPrice(String coinId, String userId, Double targetPrice) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUp(targetPrice);
			client.setTargetDown(0.0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTargetDownPrice(String coinId, String userId, Double targetPrice) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUp(0.0);
			client.setTargetDown(targetPrice);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	public boolean clearTargetPrice(String coinId, String userId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUp(0.0);
			client.setTargetDown(0.0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTimeLoop(String coinId, String userId, int timeloop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTimeloop(timeloop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateDayLoop(String coinId, String userId, int dayLoop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setDayloop(dayLoop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	public boolean updateNumber(String coinId, String userId, double number) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setCoinCnt(number);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	public boolean updateLocalTime(String coinId, String userId, Long localTime) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setLocaltime(localTime);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateLanguage(String coinId, String userId, String lang) {
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
	
	public ClientVo get(String coinId, String userId) {
		return clientDao.get(coinId, userId);
	}
	
	public ClientVo get(String coinId, int userId) {
		return this.get(coinId, String.valueOf(userId));
	}
}
