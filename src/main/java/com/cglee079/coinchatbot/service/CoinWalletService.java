package com.cglee079.coinchatbot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.dao.CoinWalletDao;
import com.cglee079.coinchatbot.model.CoinWalletVo;

@Service
public class CoinWalletService {

	@Autowired
	private CoinWalletDao coinWalletDao;

	public List<CoinWalletVo> list() {
		return coinWalletDao.list();
	}

	public CoinWalletVo get(Coin myCoinId) {
		return coinWalletDao.get(myCoinId);
	}

}