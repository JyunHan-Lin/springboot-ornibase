package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.exception.BehaviorException;
import com.example.demo.exception.BehaviorNotFoundException;
import com.example.demo.exception.DiscussException;
import com.example.demo.model.dto.BehaviorDTO;
import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.repository.BehaviorRepository;
import com.example.demo.repository.DiscussRepository;
import com.example.demo.service.BehaviorService;
import com.example.demo.service.DiscussService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ornibase/discuss/behavior")
public class BehaviorController {
	
	@Autowired
	private BehaviorService behaviorService;
	
	// 新增行為
	@PostMapping("/{discussId}")
	@ResponseBody
	public Map<String, Object> addBehavior(@PathVariable Integer discussId, BehaviorDTO behaviorDTO, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		behaviorService.saveBehavior(discussId, userId, behaviorDTO);
		
		Map<String, Object> result = new HashMap<>();
		result.put("status", "success");
		result.put("message", "行為已送出");
		return result;
	}

	
	// 顯示行為清單
	@GetMapping("/{discussId}/list")
	public String showDiscussList(@PathVariable Integer discussId, Model model, HttpSession session) {
	    UserCert userCert = (UserCert) session.getAttribute("userCert");

	    Integer userId = userCert.getUserId(); // 從登入的憑證中取得 userId
	    
	    List<BehaviorDTO> behaviorList = behaviorService.getBehaviorByDiscussId(discussId);
	    
	    model.addAttribute("behaviorList", behaviorList);
	    return "behavior/behavior-list";
	}
	
	// 編輯行為(標題、描述、網址: 點選到裡面再編輯) 
	@GetMapping("/{discussId}/edit/{behaviorId}")
	public String showEditBehavior(@PathVariable Integer behaviorId, @PathVariable Integer discussId,  Model model) {
	    BehaviorDTO behaviorDTO = behaviorService.getBehaviorById(behaviorId)
	    									     .orElseThrow(() -> new RuntimeException("not found"));
	    model.addAttribute("behaviorDTO", behaviorDTO);
	    model.addAttribute("discussId", behaviorDTO.getDiscussId());
	    return "behavior/behavior-edit"; // 編輯頁面的 JSP 名稱
	}

	@PutMapping("/{discussId}/edit/{behaviorId}")
	public String updateBehavior(@PathVariable Integer behaviorId, 
								 @PathVariable Integer discussId, 
								 @Valid BehaviorDTO behaviorDTO, 
								 BindingResult bindingResult, 
								 Model model,
								 HttpSession session) {
		try {
			// 進行修改
			UserCert userCert = (UserCert) session.getAttribute("userCert");
			behaviorDTO.setUserId(userCert.getUserId());  // 為了驗證
			behaviorService.updateBehavior(behaviorId, behaviorDTO);
			return "redirect:/ornibase/discuss/behavior/" + discussId + "/list";
			
		} catch (BehaviorException e) {
			model.addAttribute("message", e.getMessage());
			return "/message/error";
		}
	}
	
	// 刪除
	@DeleteMapping("/{discussId}/delete/{behaviorId}")
	public String deleteBehavior(@PathVariable Integer behaviorId,
								 @PathVariable Integer discussId, 
								 BehaviorDTO behaviorDTO, 
								 DiscussDTO discussDTO,
								 Model model,
								 HttpSession session) {
		try {
			UserCert userCert = (UserCert) session.getAttribute("userCert");
			behaviorService.deleteBehavior(behaviorId, userCert.getUserId());
			return "redirect:/ornibase/discuss/behavior/" + discussId + "/list"; 			
		} catch (BehaviorException e) {
			model.addAttribute("message", e.getMessage());
			return "/message/error";
		}
	}
		
}
