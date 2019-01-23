package com.cglee079.coinchatbot.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.coinchatbot.coin.CoinManager;

@Controller
public class HomeController {
	@Autowired
	private CoinManager coinManager;
	
	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/notice")
	public String notice() {
		return "client_notice";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "admin_login";
	}
	
	@RequestMapping(value = "/send")
	public String send() {
		return "client_send";
	}
	
	@RequestMapping(value = "/init")
	public String init() {
		return "coin_init";
	}
	
	@ResponseBody
	@RequestMapping(value = "/usd2krwInit.do")
	public String DoUsd2krwInit(Double usd2krw) throws IOException {
		coinManager.setExchangeRate(usd2krw);
		return new JSONObject().put("result", true).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/load.do")
	public String DoLoad() throws IOException {
		JSONObject data = new JSONObject();
		data.put("usd2krw", coinManager.getExchangeRate());
		return data.toString();
	}
}
