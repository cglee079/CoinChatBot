package com.cglee079.cointelebot.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.DailyInfoVo;

@Repository
public class DailyInfoDao {
	final static String namespace = "com.cglee079.cointelebot.mapper.DailyInfoMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(DailyInfoVo dailyInfo){
		try { return sqlSession.insert(namespace + ".insert", dailyInfo) == 1; }
		catch (Exception e){
			Log.i("ERROR\t:\t" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public DailyInfoVo get(String date, String exchange) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("date", date);
		map.put("exchange", exchange);
		return sqlSession.selectOne(namespace + ".get", map);
	}

}
