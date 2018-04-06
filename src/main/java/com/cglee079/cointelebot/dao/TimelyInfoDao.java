package com.cglee079.cointelebot.dao;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.TimelyInfoVo;

@Repository
public class TimelyInfoDao {
	final static String namespace = "com.cglee079.cointelebot.mapper.TimelyInfoMapper";

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

	public TimelyInfoVo get(String date, String exchange) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("date", date);
		map.put("exchange", exchange);
		return sqlSession.selectOne(namespace + ".get", map);
	}

	public List<TimelyInfoVo> list(String exchange, int cnt) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("exchange", exchange);
		map.put("cnt", cnt);
		return sqlSession.selectList(namespace + ".list", map);
	}

}
