package com.cglee079.coinchatbot.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.coinchatbot.dao.ClientSuggestDao;
import com.cglee079.coinchatbot.model.ClientSuggestVo;
import com.cglee079.coinchatbot.util.TimeStamper;

@Service
public class ClientSuggestService {
	Logger logger = Logger.getLogger(ClientSuggestService.class.getName());
	
	@Autowired
	private ClientSuggestDao clientSuggestDao;

	public boolean insert(String coinId, Integer userId, String username, String message) {
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
