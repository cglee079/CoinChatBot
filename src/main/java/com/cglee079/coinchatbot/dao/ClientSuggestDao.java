package com.cglee079.coinchatbot.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.ClientSuggestVo;

@Repository
public class ClientSuggestDao {
	final static String namespace = "com.cglee079.coinchatbot.mapper.ClientSuggestMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int count(Map<String, Object> map) {
		return sqlSession.selectOne(namespace + ".count", map);
	}
	
	public List<ClientSuggestVo> paging(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".list", map);
	}

	public boolean insert(ClientSuggestVo clientSuggest){
		try { return sqlSession.insert(namespace + ".insert", clientSuggest) == 1; }
		catch (Exception e){
			Log.i("ERROR\t:\t" + e.getMessage());
			e.printStackTrace();
			return false; 
		}
	}

	public int delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq);
	}
	
}
