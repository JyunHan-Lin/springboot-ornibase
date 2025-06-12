package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.UserCert;
import com.example.demo.service.DiscussService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/preview")
public class PreviewController {
	
	@GetMapping
	public String previewPage() {
		// 看得到大家建立的記錄本，但是沒辦法新增行為 ###待補
		return "preview";
	}
	
}
