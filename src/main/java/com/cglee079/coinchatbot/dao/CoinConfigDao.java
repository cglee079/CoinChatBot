package com.cglee079.coinchatbot.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.model.CoinConfigVo;

@Repository
public class CoinConfigDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.CoinConfigMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public CoinConfigVo get(String coinId) {
		return sqlSession.selectOne(namespace + ".get", coinId);
	}
}
