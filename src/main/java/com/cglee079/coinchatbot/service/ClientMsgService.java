package com.cglee079.coinchatbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import com.cglee079.coinchatbot.dao.ClientMsgDao;
import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.ClientMsgVo;
import com.cglee079.coinchatbot.util.TimeStamper;

@Service
public class ClientMsgService {
	
	@Autowired
	private ClientMsgDao clientMsgDao;

	public boolean insert(String coinId, Update update) {
		Message message = null;
		if(update.getMessage() != null) {
			message = update.getMessage();
		} else if( update.getEditedMessage() != null) {
			message = update.getEditedMessage();
		}
		
		User user = message.getFrom();
		
		ClientMsgVo clientMsg = ClientMsgVo.builder()
				.coinId(coinId)
				.userId(user.getId().toString())
				.username(user.getLastName() + " " + user.getFirstName())
				.contents(message.getText())
				.date(TimeStamper.getDateTime())
				.build();
		
		Log.i(clientMsg.log(coinId));
		return clientMsgDao.insert(clientMsg);
	}

}
