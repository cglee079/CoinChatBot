package com.cglee079.cointelebot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.dao.ClientDao;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;

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
			newDate.setTime(dateCurrent.getTime() + client.getLocalTime());
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
	
	public String getMarket(String coinId, String userId){
		ClientVo client = clientDao.get(coinId, userId);
		return client.getMarket();
	}
	
	public String getMarket(String coinId, long userId) {
		return this.getMarket(coinId, String.valueOf(userId));
	}
	
	public boolean openChat(String coinId, Integer userId, String username, String market) {
		
		ClientVo client = null;
		client = clientDao.get(coinId, userId.toString());

		if(client == null){
			client = new ClientVo();
			client.setCoinId(coinId);
			client.setMarket(market);
			client.setUserId(userId.toString());
			client.setUsername(username);
			client.setLocalTime((long)0);
			client.setLang(ID.LANG_KR);
			client.setTimeLoop(3);
			client.setDayLoop(1);
			client.setState(ID.STATE_MAIN);
			client.setOpenDate(new Date());
			client.setErrCnt(0);
			return clientDao.insert(client);
		} else{
			String enabled = client.getEnabled();
			if(enabled.equals("Y")){
				return false;
			}
			else if(enabled.equals("N")){
				client.setErrCnt(0);
				client.setEnabled("Y");
				client.setReopenDate(new Date());
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
			client.setTargetUpPrice(null);
			client.setTargetDownPrice(null);
			client.setTimeLoop(0);
			client.setDayLoop(0);
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
					client.setCloseDate(new Date());
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
			client.setMarket(market);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updatePrice(String coinId, String userId, Double price) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setPrice(price);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTargetUpPrice(String coinId, String userId, Double targetPrice) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUpPrice(targetPrice);
			client.setTargetDownPrice(0.0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTargetDownPrice(String coinId, String userId, Double targetPrice) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUpPrice(0.0);
			client.setTargetDownPrice(targetPrice);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	public boolean clearTargetPrice(String coinId, String userId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTargetUpPrice(0.0);
			client.setTargetDownPrice(0.0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTimeLoop(String coinId, String userId, int timeloop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTimeLoop(timeloop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateDayLoop(String coinId, String userId, int dayLoop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setDayLoop(dayLoop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	public boolean updateNumber(String coinId, String userId, double number) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setCoinCount(number);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	public boolean updateLocalTime(String coinId, String userId, Long localTime) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setLocalTime(localTime);
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
	
	public ClientVo get(String coinId, String userId) {
		return clientDao.get(coinId, userId);
	}
	
	public ClientVo get(String coinId, int userId) {
		return this.get(coinId, String.valueOf(userId));
	}
}
