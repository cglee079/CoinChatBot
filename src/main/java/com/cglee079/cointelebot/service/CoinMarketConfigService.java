package com.cglee079.cointelebot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.CoinMarketConfigDao;
import com.cglee079.cointelebot.model.CoinMarketConfigVo;

@Service
public class CoinMarketConfigService {

	@Autowired
	private CoinMarketConfigDao coinMarketConfigDao;

	public List<CoinMarketConfigVo> list(String coinId) {
		return coinMarketConfigDao.list(coinId);
	}
}