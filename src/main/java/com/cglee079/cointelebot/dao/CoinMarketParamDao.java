package com.cglee079.cointelebot.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CoinMarketParamDao {
	final static String namespace = "com.cglee079.cointelebot.mapper.CoinMarketParamMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public HashMap<String, String> get(String market) {
		return sqlSession.selectOne(namespace + ".get", market);
	}
}
