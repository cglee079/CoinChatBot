package com.cglee079.cointelebot.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.cointelebot.model.CoinInfoVo;
import com.cglee079.cointelebot.model.CoinWalletVo;

@Repository
public class CoinWalletDao {
	final static String namespace = "com.cglee079.cointelebot.mapper.CoinWalletMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<CoinWalletVo> list() {
		return sqlSession.selectList(namespace + ".list");
	}

	public CoinWalletVo get(String myCoin) {
		return sqlSession.selectOne(namespace + ".get", myCoin);
	}

}
