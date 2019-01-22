package com.cglee079.coinchatbot.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.model.CoinInfoVo;

@Repository
public class CoinInfoDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.CoinInfoMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<CoinInfoVo> list(String coinId) {
		return sqlSession.selectList(namespace + ".list", coinId);
	}

	public CoinInfoVo get(String coinId) {
		return sqlSession.selectOne(namespace + ".get", coinId);
	}
}
