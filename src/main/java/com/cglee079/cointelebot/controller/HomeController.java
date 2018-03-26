package com.cglee079.cointelebot.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.telegram.TelegramBot;

@Controller
public class HomeController {

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private TelegramBot telegramBot;
	
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
	@RequestMapping(value = "/notice.do")
	public String DoNotice(String text) throws IOException {
		List<ClientVo> clients = clientService.list();
		int size = clients.size();
		ClientVo client = null;
		for(int i =0; i < size; i++){
			client = clients.get(i);
			telegramBot.sendMessage(client.getUserId(), null, text, null);
		}
		return new JSONObject().put("result", true).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/send.do")
	public String DoSend(String id, String text) throws IOException {
		telegramBot.sendMessage(id, null, text, null);
		return new JSONObject().put("result", true).toString();
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
