package com.cglee079.coinchatbot.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.ClientMsgVo;

@Repository
public class ClientMessageDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.ClientMessageMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	

	public int count(Map<String, Object> map) {
		return sqlSession.selectOne(namespace + ".count", map);
	}
	
	public List<ClientMsgVo> list(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".list", map);
	}

	public boolean insert(ClientMsgVo clientMsg){
		try { return sqlSession.insert(namespace + ".insert", clientMsg) == 1; }
		catch (Exception e){
			Log.i("ERROR\t:\t" + e.getMessage());
			e.printStackTrace();
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
