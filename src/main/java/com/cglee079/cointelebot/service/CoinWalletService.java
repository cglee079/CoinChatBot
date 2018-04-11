package com.cglee079.cointelebot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.CoinWalletDao;
import com.cglee079.cointelebot.model.CoinWalletVo;

@Service
public class CoinWalletService {

	@Autowired
	private CoinWalletDao coinWalletDao;

	public List<CoinWalletVo> list() {
		return coinWalletDao.list();
	}

	public CoinWalletVo get(String myCoin) {
		return coinWalletDao.get(myCoin);
	}

}