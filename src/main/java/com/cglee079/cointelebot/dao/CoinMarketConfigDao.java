package com.cglee079.cointelebot.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.cointelebot.model.CoinMarketConfigVo;

@Repository
public class CoinMarketConfigDao {
	final static String namespace = "com.cglee079.cointelebot.mapper.CoinMarketConfigMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<CoinMarketConfigVo> list(String coinId) {
		return sqlSession.selectList(namespace + ".list", coinId);
	}
}
