package com.cglee079.coinchatbot.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.model.ClientMessageVo;

@Repository
public class ClientMessageDao {
	private Log log = LogFactory.getLog(ClientMessageDao.class);
	
	final static String namespace = "com.cglee079.coinchatbot.mapper.ClientMessageMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	

	public int count(Map<String, Object> map) {
		return sqlSession.selectOne(namespace + ".count", map);
	}
	
	public List<ClientMessageVo> list(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".list", map);
	}

	public boolean insert(ClientMessageVo clientMsg){
		try { return sqlSession.insert(namespace + ".insert", clientMsg) == 1; }
		catch (Exception e){
			log.error(e);
			return false;
		}
	}

	public int delete(String userId) {
		return sqlSession.delete(namespace + ".delete", userId);
	}
	
	public List<Map<String, Object>> countTotalMessage() {
		return sqlSession.selectList(namespace + ".countTotalMessage");
	}
	

}
