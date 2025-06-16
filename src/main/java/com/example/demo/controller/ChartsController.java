package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.exception.DiscussEditException;
import com.example.demo.exception.DiscussException;
import com.example.demo.model.dto.BehaviorDTO;
import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.BehaviorService;
import com.example.demo.service.DiscussService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ornibase/discuss")
public class ChartsController {

	@Autowired
	private DiscussService discussService;
	
	@Autowired
	private BehaviorService behaviorService;
	

	
	// 建立後的頁面
		@GetMapping("/{discussId}")
		public String viewReport(@PathVariable Integer discussId, 
								 @RequestParam(required = false) String subject,
								 @RequestParam(required = false) LocalDate date,
								 @RequestParam(required = false) LocalDate fromDate,
								 Model model, 
				                 HttpSession session) {
		    DiscussDTO discussDTO = discussService.getDiscussById(discussId)
		    									  .orElseThrow(() -> new DiscussException("DiscussDTO not found"));
		    // 取出登入者
		    UserCert userCert = (UserCert) session.getAttribute("userCert");
		    Integer privilegeLevel = 1;
		    
		    if (discussDTO.getIsPublic()) {
		        // 公開討論串，訪客能閱覽，不設額外檢查
		        if (userCert != null) {
		            Integer userId = userCert.getUserId();
		            if (userId.equals(discussDTO.getUserId())) {
		                privilegeLevel = 3; // 建立者
		            } else if (discussService.hasUserFavorited(userId, discussId)) {
		                privilegeLevel = 2; // 已收藏
		            }
		        }
		    } else {
		        // 私人討論串
		        if (userCert != null && userCert.getUserId().equals(discussDTO.getUserId())) {
		            privilegeLevel = 3; // 建立者可看
		        } else {
		            throw new RuntimeException("無權限檢視該討論串");
		        }
		    }

		    model.addAttribute("discussDTO", discussDTO);
		    model.addAttribute("privilegeLevel", privilegeLevel);
		    model.addAttribute("creatorName", discussDTO.getCreatorName()); // 加這行
		    return "discuss/discuss-view";

		}
}

