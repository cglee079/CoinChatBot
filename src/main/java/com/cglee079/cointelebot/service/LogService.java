package com.cglee079.cointelebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.LogDao;
import com.cglee079.cointelebot.model.LogVo;

@Service
public class LogService {

	@Autowired
	private LogDao logDao;

	public boolean insert(String date, String message) {
		LogVo log = new LogVo();
		log.setDate(date);
		log.setMessage(message);
		return logDao.insert(log);
	}
}