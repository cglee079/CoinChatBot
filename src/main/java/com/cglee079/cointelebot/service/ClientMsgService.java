package com.cglee079.cointelebot.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Update;

import com.cglee079.cointelebot.dao.ClientMsgDao;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientMsgVo;

@Service
public class ClientMsgService {
	
	@Autowired
	private ClientMsgDao clientMsgDao;

	public boolean insert(Update update) {
		ClientMsgVo clientMsg = new ClientMsgVo();
		clientMsg.setUserId(update.getMessage().getFrom().getId().toString());
		clientMsg.setUsername(update.getMessage().getFrom().getLastName() + " " + update.getMessage().getFrom().getFirstName());
		clientMsg.setMsg(update.getMessage().getText());
		clientMsg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Log.i(clientMsg.log());
		return clientMsgDao.insert(clientMsg);
	}

}
