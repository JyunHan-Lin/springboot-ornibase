package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/email/confirm")
public class EmailConfirmController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String confirmPage(@RequestParam String username, Model model) {
	    boolean success = userService.confirmEmail(username);
	    if (success) {
	        return "message/mailsuccess"; // 驗證成功, 導入JSP
	    } else {
	        model.addAttribute("message", "驗證失敗，請重新註冊或聯絡管理員");
	        return "message/mailfail"; // 驗證失敗, 導入JSP
	    }
	}

	
}
