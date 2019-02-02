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

import com.cglee079.coinchatbot.model.ClientSuggestVo;
import com.cglee079.coinchatbot.service.ClientSuggestService;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ClientSuggestController {
	@Autowired
	private ClientSuggestService clientSuggestService;
	
	/* 사용자 제안 보기, 페이지 이동 */
	@RequestMapping(value = "/client-suggests", method=RequestMethod.GET)
	public String clientSuggestList(Model model, @RequestParam Map<String,Object> map){
		model.addAllAttributes(map);
		return "suggest/suggest_list";
	}
	
	/* 사용자 제안 보기, 데이터 갱신, Ajax*/
	@ResponseBody
	@RequestMapping(value = "/client-suggests/records", method=RequestMethod.GET)
	public String doClientSuggestList(@RequestParam Map<String,Object> map ){
		List<ClientSuggestVo> clientMsgs = clientSuggestService.paging(map);
		int count = clientSuggestService.count(map);
		Gson gson = new Gson();
		JSONArray data = new JSONArray(gson.toJson(clientMsgs));
		
		JSONObject resultMap = new JSONObject();
		resultMap.put("rows", data);
		resultMap.put("total", count);

		return resultMap.toString();
	}
	
	/* 사용자 제안 삭제, Ajax */
	@ResponseBody
	@RequestMapping(value = "/client-suggests/{seq}", method=RequestMethod.DELETE)
	public String doClientDelete(@PathVariable int seq){
		clientSuggestService.delete(seq);
		return new JSONObject().put("result", true).toString();
	}
	
}
