package com.cglee079.coinchatbot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.model.CoinMarketConfigVo;

@Repository
public class CoinMarketConfigDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.CoinMarketConfigMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<CoinMarketConfigVo> list(Market marketId, Coin coinId) {
		Map<String, Object> map = new HashMap<>();
		map.put("marketId", marketId);
		map.put("coinId", coinId);
		return sqlSession.selectList(namespace + ".list", map);
	}
}
