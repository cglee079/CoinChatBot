package com.cglee079.coinchatbot.controller;

import java.util.List;
import java.util.Map;

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

import com.cglee079.coinchatbot.model.ClientMessageVo;
import com.cglee079.coinchatbot.service.ClientMessgeService;
import com.google.gson.Gson;

@Controller
public class ClientMessgeController {
	@Autowired
	private ClientMessgeService clientMessageService;
	
	/* 사용자 메시지 보기, 페이지 이동 */
	@RequestMapping(value = "/client-messages", method=RequestMethod.GET)
	public String clientMsgList(Model model, @RequestParam Map<String,Object> map){
		model.addAllAttributes(map);
		return "message/message_list";
	}
	
	/* 사용자 메시지 검색 */
	@RequestMapping(value = "/client-messages/{userId}", method=RequestMethod.GET)
	public String clientMsgSearch(Model model, @PathVariable String userId){
		model.addAttribute("userId", userId);
		return "message/message_list";
	}
	
	/* 사용자 메세지 보기 , 데이터 갱신 , Ajax*/
	@ResponseBody
	@RequestMapping(value = "/client-messages/records", method=RequestMethod.GET)
	public String doClientMsgList(@RequestParam Map<String,Object> map ){
		List<ClientMessageVo> clientMsgs = clientMessageService.list(map);
		int count = clientMessageService.count(map);
		Gson gson = new Gson();
		JSONArray data = new JSONArray(gson.toJson(clientMsgs));
		
		JSONObject resultMap = new JSONObject();
		resultMap.put("rows", data);
		resultMap.put("total", count);

		return resultMap.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/client-messages/my", method=RequestMethod.DELETE)
	public String doClearMyMessage() {
		int cnt = clientMessageService.deleteMyMessage();
		JSONObject result = new JSONObject();
		result.put("cnt", cnt);
		return result.toString();
	}
	
	@RequestMapping(value = "/client-messages/stat", method=RequestMethod.GET)
	public String statClientMsg(Model model) {
		Map<String, Object> map = clientMessageService.countTotalMessage();
		model.addAllAttributes(map);
		return "message/message_stat";
	}
	
}
