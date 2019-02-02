package com.cglee079.coinchatbot.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public int count(Map<String, Object> map) {
		return sqlSession.selectOne(namespace + ".count", map);
	}
	
	public List<ClientVo> list(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".list", map);
	}
	
	public List<ClientVo> listAlertLoop(Coin coinId, Market marketId, Integer timeLoop, Integer dayLoop){
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("marketId", marketId);
		map.put("timeLoop", timeLoop);
		map.put("dayLoop", dayLoop);
		return sqlSession.selectList(namespace + ".listAlertLoop", map);
	}
	
	public List<ClientVo> listAlertTargetUp(Coin coinId, Market marketId, double coinValue) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("marketId", marketId);
		map.put("coinValue", coinValue);
		return sqlSession.selectList(namespace + ".listAlertTargetUp", map);
	}
	
	public List<ClientVo> listAlertTargetDown(Coin coinId, Market marketId, double coinValue) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("marketId", marketId);
		map.put("coinValue", coinValue);
		return sqlSession.selectList(namespace + ".listAlertTargetDown", map);
	}
	
	public boolean insert(ClientVo client){
		return sqlSession.insert(namespace + ".insert", client) == 1;
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

	public boolean delete(ClientVo client) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", client.getCoinId());
		map.put("userId", client.getUserId());
		return sqlSession.delete(namespace + ".delete", map) == 1;
	}
	
	/* 통계 관련 카운트 */
	public int countChat(Boolean enabled) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("enabled", enabled);
		return sqlSession.selectOne(namespace + ".countChat", map);
	}


	public Object countUser(Boolean enabled) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("enabled", enabled);
		return sqlSession.selectOne(namespace + ".countUser", map);
	}


	public List<Map<String, Object>> countNewClientInToday() {
		return sqlSession.selectList(namespace + ".countNewClientInToday");
	}
	
	public List<Map<String, Object>> countNewClientInTomonth() {
		return sqlSession.selectList(namespace + ".countNewClientInTomonth");
	}

}
