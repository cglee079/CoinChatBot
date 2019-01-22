package com.cglee079.coinchatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.dao.CoinConfigDao;
import com.cglee079.coinchatbot.model.CoinConfigVo;

@Service
public class CoinConfigService {

	@Autowired
	private CoinConfigDao coinConfigDao;

	public CoinConfigVo get(String coinId) {
		return coinConfigDao.get(coinId);
	}
}