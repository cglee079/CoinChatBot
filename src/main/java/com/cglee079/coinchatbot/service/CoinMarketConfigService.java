package com.cglee079.coinchatbot.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.dao.CoinMarketConfigDao;
import com.cglee079.coinchatbot.model.CoinMarketConfigVo;

@Service
public class CoinMarketConfigService {

	@Autowired
	private CoinMarketConfigDao coinMarketConfigDao;

	public HashMap<Coin, String> getMarketParams(String market){
		HashMap<Coin, String> map = new HashMap<>();
		List<CoinMarketConfigVo> configs = coinMarketConfigDao.list(market, null);
		CoinMarketConfigVo config = null;
		for(int i = 0; i < configs.size(); i++) {
			config = configs.get(i);
			map.put(config.getCoinId(), config.getParam());
		}
		return map;
	}
	
	public List<CoinMarketConfigVo> list(Coin coinId) {
		return coinMarketConfigDao.list(null, coinId);
	}
}