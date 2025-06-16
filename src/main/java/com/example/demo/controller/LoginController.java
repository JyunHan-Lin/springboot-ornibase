package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.CertException;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.CertService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private CertService certService;
	
	@GetMapping
	public String loginPage() {
		return "login";
	}
	
	@PostMapping
	public String checkLogin(@RequestParam String username, 
			                 @RequestParam String password, 
			                 @RequestParam String authcode,
			                 Model model, HttpSession session) {
	// 比對驗證碼, 後面 + "" 是為了避免空值 (驗證碼加上時間?)	
    String sessionAuthCode = (String) session.getAttribute("authcode") + "";
	
	// 取得憑證
	UserCert userCert = null;
	try {
		userCert = certService.getCert(username, password, authcode, sessionAuthCode);
	} catch (CertException e) {
		/**
		 * 	validate: 一般指驗證資料是否正確或檢查 session 是否有效
		 * 	invalidate: 讓整個 session 失效 (清除使用者所有登入資料) 
		 **/
		session.invalidate();
		// 將錯誤資料丟給 error.jsp
		model.addAttribute("message", e.getMessage());
		e.printStackTrace();
		return "message/error";
	}
	// 將憑證放到 session
	session.setAttribute("userCert", userCert);
	session.setAttribute("userId", userCert.getUserId());
	session.setAttribute("userName", username);

	return "redirect:/ornibase"; // 重導到首頁

	}


}
