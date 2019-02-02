package com.cglee079.coinchatbot.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cglee079.coinchatbot.service.ClientService;


@Controller
public class HomeController {
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("user", clientService.countUser(true));
		return "home";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "admin_login";
	}
}
	