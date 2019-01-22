package com.cglee079.coinchatbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.dao.CoinInfoDao;
import com.cglee079.coinchatbot.model.CoinInfoVo;

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