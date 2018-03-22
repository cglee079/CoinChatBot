package com.cglee079.cointelebot.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.constants.C;
import com.cglee079.cointelebot.dao.ClientDao;
import com.cglee079.cointelebot.model.ClientVo;

@Service
public class ClientService {

	@Autowired
	private ClientDao clientDao;

	public List<ClientVo> list() {
		return clientDao.list();
	}
	
	public List<ClientVo> list(int timeLoop) {
		return clientDao.list(timeLoop);
	}
	
	public List<ClientVo> list(String exchange) {
		return clientDao.list(exchange);
	}
	
	public List<ClientVo> list(String exchange, Integer timeLoop, Integer dayLoop, Integer currentPrice) {
		return clientDao.list(exchange, timeLoop, dayLoop, currentPrice);
	}
	
	public String getExchange(String userId){
		ClientVo client = clientDao.get(userId);
		if(client != null) {
			return client.getExchange();
		} else {
			if(C.ENABLED_COINONE) { return C.EXCHANGE_COINONE; }
			if(C.ENABLED_BITHUMB) { return C.EXCHANGE_BITHUMB; }
			if(C.ENABLED_UPBIT) { return C.EXCHANGE_UPBIT; }
			return C.EXCHANGE_COINONE;
		}
	}
	
	public String getExchange(long userId) {
		return this.getExchange(String.valueOf(userId));
	}
	
	public boolean openChat(Integer userId, String username) {
		
		ClientVo client = null;
		client = clientDao.get(userId.toString());

		if(client == null){
			client = new ClientVo();
			client.setUserId(userId.toString());
			client.setUsername(username);
			client.setTimeLoop(3);
			client.setDayLoop(1);
			client.setOpenDate(new Date());
			return clientDao.insert(client);
		} else{
			String enabled = client.getEnabled();
			if(enabled.equals("Y")){
				return false;
			}
			else if(enabled.equals("N")){
				client.setEnabled("Y");
				client.setReopenDate(new Date());
				return clientDao.update(client);
			}
			else{
				return false;
			}
		}
	}

	public boolean closeChat(String userId) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			if(client.getEnabled().equals("Y")){
				client.setEnabled("N");
				client.setCloseDate(new Date());
				return clientDao.update(client);
			} else{
				return false;
			}
		} else{
			return false;
		}
	}
	
	public boolean closeChat(int userId) {
		return this.closeChat(String.valueOf(userId));
	}
	
	public boolean stopChat(int userId) {
		ClientVo client = clientDao.get(String.valueOf(userId));
		if(client != null) {
			client.setTargetUpPrice(null);
			client.setTargetDownPrice(null);
			client.setTimeLoop(0);
			client.setDayLoop(0);
			return clientDao.update(client);
		}
		return false;
	}

	public boolean updateExchange(String userId, String exchange) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setExchange(exchange);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updatePrice(String userId, int price) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setAvgPrice(price);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTargetUpPrice(String userId, Integer targetPrice) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setTargetUpPrice(targetPrice);
			client.setTargetDownPrice(0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTargetDownPrice(String userId, Integer targetPrice) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setTargetUpPrice(0);
			client.setTargetDownPrice(targetPrice);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	public boolean clearTargetPrice(String userId) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setTargetUpPrice(0);
			client.setTargetDownPrice(0);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateTimeLoop(String userId, int timeloop) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setTimeLoop(timeloop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	
	public boolean updateDayLoop(String userId, int dayLoop) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setDayLoop(dayLoop);
			return clientDao.update(client);
		} else{
			return false;
		}
	}
	

	public boolean updateNumber(String userId, double number) {
		ClientVo client = clientDao.get(userId);
		if(client != null){
			client.setCoinCount(number);
			return clientDao.update(client);
		} else{
			return false;
		}
	}

	public ClientVo get(String userId) {
		return clientDao.get(userId);
	}
	
	public ClientVo get(int userId) {
		return this.get(String.valueOf(userId));
	}

}
