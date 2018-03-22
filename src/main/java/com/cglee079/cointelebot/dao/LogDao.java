package com.cglee079.cointelebot.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.cointelebot.model.LogVo;

@Repository
public class LogDao {
	final static String namespace = "com.cglee079.cointelebot.mapper.LogMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(LogVo log){
		try { return sqlSession.insert(namespace + ".insert", log) == 1; }
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
