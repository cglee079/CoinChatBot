package com.cglee079.coinchatbot.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.coinchatbot.service.PostMessageService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PostMessageController {
	
	@Autowired
	PostMessageService postMessageService;
	
	/* 사용자에게 메세지 보내기, 페이지 이동 */
	@RequestMapping(value = "/messages/post", method = RequestMethod.GET)
	public String postMessage(Model model, String caseId, @RequestParam Map<String,Object> map){
		Map<String, Object> result = postMessageService.view(map, caseId);
		
		model.addAllAttributes(map);
		model.addAllAttributes(result);
		return "post/post_message";
	}
	
	/* 사용자에게 메세지 보내기 , Ajax*/
	@ResponseBody
	@RequestMapping(value = "/messages/post", method=RequestMethod.POST)
	public String doPostMessage(@RequestParam Map<String,Object> map){
		JSONObject result = postMessageService.post(map);
		return result.toString();
	}
	
}
