package com.cglee079.cointelebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import com.cglee079.cointelebot.dao.ClientMsgDao;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientMsgVo;
import com.cglee079.cointelebot.util.TimeStamper;

@Service
public class ClientMsgService {
	
	@Autowired
	private ClientMsgDao clientMsgDao;

	public boolean insert(String coinId, Update update) {
		ClientMsgVo clientMsg = new ClientMsgVo();
		Message message = null;
		if(update.getMessage() != null) {
			message = update.getMessage();
		} else if( update.getEditedMessage() != null) {
			message = update.getEditedMessage();
		}
		
		User user = message.getFrom();
		clientMsg.setCoinId(coinId);
		clientMsg.setUserId(user.getId().toString());
		clientMsg.setUsername(user.getLastName() + " " + user.getFirstName());
		clientMsg.setMsg(message.getText());
		clientMsg.setDate(TimeStamper.getDateTime());
		
		Log.i(clientMsg.log(coinId));
		return clientMsgDao.insert(clientMsg);
	}

}
