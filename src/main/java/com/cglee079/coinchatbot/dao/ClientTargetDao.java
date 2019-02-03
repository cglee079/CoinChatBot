package com.cglee079.coinchatbot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.model.ClientTargetVo;

@Repository
public class ClientTargetDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.ClientTargetMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	public List<ClientTargetVo> list(Coin coinId, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coinId", coinId);
		map.put("userId", userId);
		return sqlSession.selectList(namespace + ".list", map);
	}


	public List<ClientTargetVo> listForAlert(Coin coinId, Market marketId, double coinValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coinId", coinId);
		map.put("marketId", marketId);
		map.put("coinValue", coinValue);
		return sqlSession.selectList(namespace + ".listForAlert", map);
	}
	
	public boolean insert(ClientTargetVo target) {
		try {
			return sqlSession.insert(namespace + ".insert", target) == 1;
		} catch(DuplicateKeyException e) {
			return false;
		}
		
	}


	public boolean delete(Coin coinId, String userId, Double price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coinId", coinId);
		map.put("userId", userId);
		map.put("price", price);
		
		return sqlSession.delete(namespace + ".delete", map) == 1;
		
	}


	public boolean updatePrice(ClientTargetVo target, double price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("target", target);
		map.put("price", price);
		return sqlSession.update(namespace + ".updatePrice", map) == 1;
	}

}
