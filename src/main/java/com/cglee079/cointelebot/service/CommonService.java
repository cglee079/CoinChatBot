package com.cglee079.cointelebot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.cointelebot.dao.CommonDao;

@Service
public class CommonService {

	@Autowired
	private CommonDao commonDao;

	public String get(String id) {
		return commonDao.get(id);
	}

}