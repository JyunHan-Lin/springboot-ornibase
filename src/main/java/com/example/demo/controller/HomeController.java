package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.DiscussService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ornibase")
public class HomeController {

	@Autowired
	private DiscussService discussService;
	
	@GetMapping
	public String mainPage(Model model) {
		// 看得到大家建立的記錄本
		model.addAttribute("discussList", discussService.getPublicDiscussList());
		return "main";
	}
	
    @GetMapping("/search")
    public String searchDiscusses(@RequestParam(required = false) String keyword, Model model) {
        List<DiscussDTO> discussList = discussService.searchDiscusses(keyword);
        model.addAttribute("discussList", discussList);
        model.addAttribute("keyword", keyword);
        return "redirect:/ornibase";  // JSP 頁面路徑，自己調整
    }
	
	// 登出
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login"; 
	}
	
}
