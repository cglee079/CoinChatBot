package com.cglee079.cointelebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.CoinConfigDao;
import com.cglee079.cointelebot.model.CoinConfigVo;

@Service
public class CoinConfigService {

	@Autowired
	private CoinConfigDao coinConfigDao;

	public CoinConfigVo get(String coinId) {
		return coinConfigDao.get(coinId);
	}
}