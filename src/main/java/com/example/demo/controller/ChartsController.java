package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		    Integer userId = userCert != null ? userCert.getUserId() : null; // 預設 userId (測試環境)
		    
		    // 安全驗證：只允許建立者瀏覽
		    if (!userId.equals(discussDTO.getUserId())) {
		    	throw new DiscussEditException("並非討論串建立者無法檢視討論串");
		    }
		    
		    model.addAttribute("discussDTO", discussDTO);

		    // 行為資料
		    List<BehaviorDTO> behaviors = behaviorService.getBehaviorsByDiscussAndUser(discussId, userId);


		    model.addAttribute("timelineDataJson", toJson(behaviorService.getTimelineData(date)));
		    return "discuss/discuss";
		}
		
		private String toJson(Object obj) {
		    try {
		        return new ObjectMapper().writeValueAsString(obj);
		    } catch (Exception e) {
		        return "{}";
		    }
		}
		@PostMapping("/{discussId}/timeline")
		@ResponseBody
		public List<Map<String, Object>> getTimelineData(@PathVariable Integer discussId, @RequestParam LocalDate date) {
		    return behaviorService.getTimelineData(date);
		}

}

