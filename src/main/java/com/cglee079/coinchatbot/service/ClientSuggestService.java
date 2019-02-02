package com.cglee079.coinchatbot.service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.dao.ClientSuggestDao;
import com.cglee079.coinchatbot.model.ClientSuggestVo;
import com.cglee079.coinchatbot.util.TimeStamper;

@Service
public class ClientSuggestService {
	Logger logger = Logger.getLogger(ClientSuggestService.class.getName());
	
	@Autowired
	private ClientSuggestDao clientSuggestDao;

	public List<ClientSuggestVo> paging(Map<String, Object> map) {
		int page = Integer.parseInt((String) map.get("page"));
		int rows = Integer.parseInt((String) map.get("rows"));
		int stRow = (page * rows) - rows;
		map.put("stRow", stRow);
		return clientSuggestDao.paging(map);
	}

	public int count(Map<String, Object> map) {
		return clientSuggestDao.count(map);
	}

	public int delete(int seq) {
		return clientSuggestDao.delete(seq);
	}
	
	public boolean insert(Coin coinId, Integer userId, String username, String message) {
		ClientSuggestVo clientSuggest = ClientSuggestVo.builder()
				.coinId(coinId)
				.userId(userId.toString())
				.username(username)
				.contents(message)
				.date(TimeStamper.getDateTime())
				.build();
		
		return clientSuggestDao.insert(clientSuggest);
	}

}
