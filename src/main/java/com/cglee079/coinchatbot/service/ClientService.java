package com.cglee079.coinchatbot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
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

	public List<ClientVo> list(Coin coinId) {
		return clientDao.list(coinId);
	}
	
	public List<ClientVo> list(Coin coinId, Market marketId, double coinValue, boolean isUp) {
		if(isUp) { return clientDao.listTargetUp(coinId, marketId, coinValue); } 
		else { return clientDao.listTargetDown(coinId, marketId, coinValue); }
	}
	
	public List<ClientVo> list(Coin coinId, Market marketId, Integer timeLoop, Integer dayLoop){
		return clientDao.list(coinId, marketId, timeLoop, dayLoop);
	}
	
	public List<ClientVo> listAtMidnight(Coin coinId, Market marketId, Integer timeLoop, int dayLoop, Date dateCurrent) {
		List<ClientVo> newclients = new ArrayList<>();
		List<ClientVo> clients = clientDao.list(coinId, marketId, timeLoop, dayLoop);
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
	
	public String getState(Coin coinId, Integer id) {
		ClientVo client = clientDao.get(coinId, id.toString());
		if(client != null) {
			return client.getState();
		} else {
			return null;
		}
	}
	
	public Market getMarketId(Coin coinId, String userId){
		ClientVo client = clientDao.get(coinId, userId);
		return client.getMarketId();
	}
	
	public Market getMarketId(Coin coinId, long userId) {
		return this.getMarketId(coinId, String.valueOf(userId));
	}
	
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
	
	public boolean increaseErrCnt(Coin coinId, String userId) {
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
	
	public boolean updateState(Coin coinId, String userId, String state) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setState(state);
			return clientDao.update(client);
		} else{
			return false;
		}
		
	}

	public boolean updateMarketId(Coin coinId, String userId, Market marketId) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setMarketId(marketId);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updatePrice(Coin coinId, String userId, Double price) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setInvest(price);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
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
	
	public boolean updateTimeLoop(Coin coinId, String userId, int timeloop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setTimeloop(timeloop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateDayLoop(Coin coinId, String userId, int dayLoop) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setDayloop(dayLoop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	public boolean updateNumber(Coin coinId, String userId, double number) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setCoinCnt(number);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	public boolean updateLocalTime(Coin coinId, String userId, Long localTime) {
		ClientVo client = clientDao.get(coinId, userId);
		if(client != null){
			client.setLocaltime(localTime);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateLanguage(Coin coinId, String userId, String lang) {
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
}
