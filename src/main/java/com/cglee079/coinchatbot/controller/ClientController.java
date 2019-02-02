package com.cglee079.coinchatbot.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.coinchatbot.model.ClientVo;
import com.cglee079.coinchatbot.service.ClientService;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	/* 사용자 보기, 페이지 이동 */
	@RequestMapping(value = "/clients", method=RequestMethod.GET)
	public String clientList(Model model, @RequestParam Map<String, Object> map){
		model.addAllAttributes(map);
		return "client/client_list";
	}
	
	@RequestMapping(value = "/clients/{userId}", method=RequestMethod.GET)
	public String clientSearch(Model model, @PathVariable String userId){
		model.addAttribute("userId", userId);
		return "client/client_list";
	}
	
	
	/* 사용자 보기, 데이터 갱신, Ajax*/
	@ResponseBody
	@RequestMapping(value = "/clients/records", method=RequestMethod.GET)
	public String doClientList(@RequestParam Map<String,Object> map ){
		List<ClientVo> clients = clientService.list(map);
		int count = clientService.count(map);
		
		JSONObject resultMap = new JSONObject();
		resultMap.put("rows", new JSONArray(new Gson().toJson(clients)));
		resultMap.put("total", count);

		return resultMap.toString();
	}
	
	/* 사용자 정보 업데이트, Ajax*/
	@ResponseBody
	@RequestMapping(value = "/clients/{coinId}/{userId}")
	public String doClientUpdate(HttpServletRequest request, ClientVo client){
		boolean result = clientService.updateByAdmin(client);
		return new JSONObject().put("result", result).toString();
	}
	
	/* 사용자 삭제, Ajax*/
	@ResponseBody
	@RequestMapping(value = "/clients/{coinId}/{userId}", method=RequestMethod.DELETE)
	public String doClientDelete(ClientVo client){
		boolean result =clientService.delete(client);
		return new JSONObject().put("result", result).toString();
	}
	
	@RequestMapping(value = "/clients/stat")
	public String statClient(Model model) {
		model.addAttribute("newClientByDay", clientService.countNewClientInToday());
		model.addAttribute("newClientByMonth", clientService.countNewClientInTomonth());
		
		model.addAttribute("totalChat", clientService.countChat(null));
		model.addAttribute("validChat", clientService.countChat(true));
		model.addAttribute("invalidChat", clientService.countChat(false));
		
		model.addAttribute("totalUser", clientService.countUser(null));
		model.addAttribute("validUser", clientService.countUser(true));
		return "client/client_stat";
	}
	
}
