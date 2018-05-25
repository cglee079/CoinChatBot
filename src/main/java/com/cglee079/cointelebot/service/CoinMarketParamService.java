package com.cglee079.cointelebot.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.CoinMarketParamDao;

@Service
public class CoinMarketParamService {

	@Autowired
	private CoinMarketParamDao coinMarketParamDao;

	public HashMap<String, String> get(String market) {
		return coinMarketParamDao.get(market);
	}
}