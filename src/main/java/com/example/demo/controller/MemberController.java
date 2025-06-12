package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.dto.UserCert;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ornibase/member")
public class MemberController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String member() {
		return "member";
	}
	
    @PostMapping
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
    	// 從 session 取得登入者資訊
        UserCert userCert = (UserCert) session.getAttribute("userCert");
        // 取得登入者 email
        String username = userCert.getUsername();
        
        // 執行修改
        boolean result = userService.changePassword(username, oldPassword, newPassword, confirmPassword);
//        System.out.println(username);		測試用
//        System.out.println(oldPassword);	測試用
//        System.out.println(newPassword);	測試用
//        System.out.println(confirmPassword);	測試用

        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "密碼變更成功！");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "密碼變更失敗，請確認原密碼與新密碼是否符合。");
        }

        return "redirect:/ornibase/member";
    }
}
