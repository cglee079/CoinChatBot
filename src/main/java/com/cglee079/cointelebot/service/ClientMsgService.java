package com.cglee079.cointelebot.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import com.cglee079.cointelebot.dao.ClientMsgDao;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientMsgVo;

@Service
public class ClientMsgService {
	
	@Autowired
	private ClientMsgDao clientMsgDao;

	public boolean insert(Update update) {
		ClientMsgVo clientMsg = new ClientMsgVo();
		Message message = null;
		if(update.getMessage() != null) {
			message = update.getMessage();
		} else if( update.getEditedMessage() != null) {
			message = update.getEditedMessage();
		}
		
		User user = message.getFrom();
		clientMsg.setUserId(user.getId().toString());
		clientMsg.setUsername(user.getLastName() + " " + user.getFirstName());
		clientMsg.setMsg(message.getText());
		clientMsg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		Log.i(clientMsg.log());
		return clientMsgDao.insert(clientMsg);
	}

}
