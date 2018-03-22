package com.cglee079.cointelebot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.CoinInfoDao;
import com.cglee079.cointelebot.model.CoinInfoVo;

@Service
public class CoinInfoService {

	@Autowired
	private CoinInfoDao coinInfoDao;

	public List<CoinInfoVo> list(String coinId) {
		return coinInfoDao.list(coinId);
	}

	public CoinInfoVo get(String coinId) {
		return coinInfoDao.get(coinId);
	}
}