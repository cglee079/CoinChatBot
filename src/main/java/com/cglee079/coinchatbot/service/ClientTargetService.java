package com.cglee079.coinchatbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.dao.ClientTargetDao;
import com.cglee079.coinchatbot.model.ClientTargetVo;

@Service
public class ClientTargetService {
	@Autowired
	private ClientTargetDao clientTargetDao;

	public boolean insert(ClientTargetVo target) {
		return clientTargetDao.insert(target);
	}

	public List<ClientTargetVo> listForAlert(Coin coinId, Market marketId, double coinValue) {
		return clientTargetDao.listForAlert(coinId, marketId, coinValue);
	}

	public List<ClientTargetVo> list(Coin coinId, Integer userId) {
		return clientTargetDao.list(coinId, userId.toString());
	}

	public boolean delete(Coin coinId, String userId, Double price) {
		return clientTargetDao.delete(coinId, userId, price);
	}

	public boolean delete(Coin coinId, Integer userId) {
		return clientTargetDao.delete(coinId, userId.toString(), null);
	}

	public boolean updatePrice(ClientTargetVo target, double price) {
		return clientTargetDao.updatePrice(target, price);
	}

}
