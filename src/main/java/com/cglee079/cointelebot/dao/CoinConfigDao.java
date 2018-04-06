package com.cglee079.cointelebot.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.cointelebot.model.CoinConfigVo;

@Repository
public class CoinConfigDao {
	final static String namespace = "com.cglee079.cointelebot.mapper.CoinConfigMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public CoinConfigVo get(String coinId) {
		return sqlSession.selectOne(namespace + ".get", coinId);
	}
}
