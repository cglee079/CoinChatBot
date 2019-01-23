package com.cglee079.coinchatbot.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.model.ClientVo;

@Repository
public class ClientDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.ClientMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<ClientVo> list(Coin coinId) {
		return sqlSession.selectList(namespace + ".S01", coinId);
	}
	
	public List<ClientVo> list(Coin coinId, Market marketId, Integer timeLoop, Integer dayLoop){
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("marketId", marketId);
		map.put("timeLoop", timeLoop);
		map.put("dayLoop", dayLoop);
		return sqlSession.selectList(namespace + ".S01", map);
	}
	
	public List<ClientVo> listTargetUp(Coin coinId, Market marketId, double coinValue) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("marketId", marketId);
		map.put("coinValue", coinValue);
		return sqlSession.selectList(namespace + ".S02", map);
	}
	
	public List<ClientVo> listTargetDown(Coin coinId, Market marketId, double coinValue) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("marketId", marketId);
		map.put("coinValue", coinValue);
		return sqlSession.selectList(namespace + ".S03", map);
	}
	
	public boolean insert(ClientVo client){
		return sqlSession.insert(namespace + ".insert", client) == 1;
	}

	public boolean delete(Coin coinId, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("userId", userId);
		return sqlSession.delete(namespace + ".delete", map) == 1;
	}

	public boolean update(ClientVo client) {
		return sqlSession.update(namespace + ".update", client) == 1;
	}

	public ClientVo get(Coin coinId, String userId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("userId", userId);
		return sqlSession.selectOne(namespace + ".get", map);
	}

}
