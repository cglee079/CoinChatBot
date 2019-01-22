package com.cglee079.coinchatbot.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.TimelyInfoVo;

@Repository
public class TimelyInfoDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.TimelyInfoMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(TimelyInfoVo timelyInfo){
		try { return sqlSession.insert(namespace + ".insert", timelyInfo) == 1; }
		catch (Exception e){
			Log.i("ERROR\t:\t" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public TimelyInfoVo get(Coin coinId, String date, Market marketId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("date", date);
		map.put("market", marketId);
		return sqlSession.selectOne(namespace + ".get", map);
	}

	public List<TimelyInfoVo> list(String coinId, Market marketId, int cnt) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("coinId", coinId);
		map.put("market", marketId);
		map.put("cnt", cnt);
		return sqlSession.selectList(namespace + ".list", map);
	}

}
