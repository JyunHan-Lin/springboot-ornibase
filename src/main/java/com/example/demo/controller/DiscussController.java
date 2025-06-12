package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.DiscussService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/ornibase/discuss")
public class DiscussController {
	
	@Autowired
	private DiscussService discussService;
	
	// 檢視記錄本
	@GetMapping
	public String mainPage(Model model, String youtubeVideoId, HttpSession session) {
	    UserCert userCert = (UserCert) session.getAttribute("userCert");
	    Integer userId = userCert.getUserId();
	    model.addAttribute("discussList", discussService.getDiscussByUserId(userId));
		return "discuss/discuss-list";
	}
	
	// 建立討論串的頁面
	@GetMapping("/new")
	public String createDiscuss(Model model) {
		model.addAttribute("discussDTO", new DiscussDTO());
		return "discuss/discuss-new";
	}
	
	// 建立討論串
	@PostMapping("/new")
	public String saveDiscuss(@ModelAttribute DiscussDTO discussDTO, HttpSession session) {
		UserCert userCert = (UserCert) session.getAttribute("userCert");
		// 將 userId 放入 DTO
	    discussDTO.setUserId(userCert.getUserId());
		
		DiscussDTO savedDiscuss = discussService.createDiscuss(discussDTO);
	    return "redirect:/ornibase/discuss/" + savedDiscuss.getDiscussId();
	}
	
	// 編輯討論串(標題、描述、網址: 點選到裡面再編輯) 
	@GetMapping("/update/{discussId}")
	public String showEditDiscuss(@PathVariable Integer discussId, Model model) {
	    DiscussDTO discussDTO = discussService.getDiscussById(discussId)
	    									  .orElseThrow(() -> new RuntimeException("DiscussDTO not found"));
	    model.addAttribute("discussDTO", discussDTO);
	    return "discuss/discuss-edit"; // 編輯頁面的 JSP 名稱
	}

	@PutMapping("/update/{discussId}")
	public String updateRoom(@PathVariable Integer discussId, @Valid DiscussDTO discussDTO, BindingResult bindingResult, HttpSession session) {
	    UserCert userCert = (UserCert) session.getAttribute("userCert");
	    Integer userId = userCert.getUserId();
		// 進行修改
		discussService.updateDiscuss(discussId, userId, discussDTO);
		return "redirect:/ornibase/discuss/" + discussId;
	}

	
	// 刪除討論串
	@DeleteMapping("/delete/{discussId}")
	public String deleteRoom(@PathVariable Integer discussId,  DiscussDTO discussDTO, HttpSession session) {
	    UserCert userCert = (UserCert) session.getAttribute("userCert");
	    Integer userId = userCert.getUserId();
		discussService.deleteDiscuss(discussId, userId, discussDTO);
		return "redirect:/ornibase"; // 重導到首頁
	}
	
}
