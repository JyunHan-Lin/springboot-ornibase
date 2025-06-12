package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.MailInvalidException;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import com.example.demo.util.HashUtil;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping
	public String registerPage() {
		return "register";
	}
	/**	使用者註冊 (新增使用者)
	 * 	@RequestParam：自動接收表單中的 username、password、email 欄位值
	 * 	Model：用來傳值到 JSP 畫面顯示訊息 (用來在「Controller 與 JSP 頁面之間傳遞資料」的容器)
	 **/
	@PostMapping
	public String addUser(@RequestParam String username,
	                      @RequestParam String password,
	                      @RequestParam String confirmPassword,
	                      @RequestParam String email,
	                      RedirectAttributes redirectAttributes) {

	    try {
	    	boolean success = userService.addUser(username, password, confirmPassword, email, false, "user");
	    
	    	if (success) {	    		
	    		// 寄送 email 驗證信 (使用 emailService 中的 sendEmail 方法)
	    		String emailConfirmLink = "http://localhost:8084/email/confirm?username=" + username;
	    		emailService.sendEmail(email, emailConfirmLink);
	            return "redirect:/register?status=success";
			}  
	    		return "redirect:/register?status=fail";
	    	
	    } catch (MailInvalidException e) {
	    		return "redirect:/register?status=fail";
		}
	}
}