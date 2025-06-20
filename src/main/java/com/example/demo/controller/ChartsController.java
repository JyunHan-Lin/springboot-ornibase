package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.model.entity.Discuss;
import com.example.demo.service.BehaviorService;
import com.example.demo.service.CommentService;
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
	
	@Autowired
	private CommentService commentService;
	
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
		                privilegeLevel = 2; // 非建立者已收藏
		            }
		        }
		    } else {
		        // 私人討論串
		        if (userCert != null && userCert.getUserId().equals(discussDTO.getUserId())) {
		            privilegeLevel = 3; // 建立者
		        } else {
		            throw new RuntimeException("無權限檢視該討論串");
		        }
		    }

		    // 只要是建立者或收藏者，都可以看到行為清單
		    List<BehaviorDTO> behaviorList = null;
		    if (privilegeLevel >= 2) {
		        behaviorList = behaviorService.getBehaviorByDiscussId(discussId);
		    }
		    
		    // 轉回 Discuss entity，給 CommentService 用
		    Discuss discuss = discussService.getDiscussEntityById(discussId)
		                        .orElseThrow(() -> new RuntimeException("Discuss entity not found"));

		    List<CommentDTO> commentList = commentService.getCommentDTOs(discuss);
		    model.addAttribute("today", LocalDate.now().toString());
		    model.addAttribute("comments", commentList);
		    model.addAttribute("behaviorList", behaviorList);
		    model.addAttribute("discussDTO", discussDTO);
		    model.addAttribute("privilegeLevel", privilegeLevel);
		    model.addAttribute("creatorName", discussDTO.getCreatorName()); 
		    model.addAttribute("behaviorDTO", new BehaviorDTO());
		    return "discuss/discuss";

		}
	    
	    // 圖表
	    @GetMapping("/chart-timeline")
	    @ResponseBody
	    public List<BehaviorDTO> getTimelineData(@RequestParam Integer discussId) {
	    	LocalDate today = LocalDate.now(); // 自動抓今天
	    	return behaviorService.getBehaviorByDiscussIdAndDate(discussId, today);
	    }

	    @PostMapping("/{discussId}/comment")
	    @ResponseBody
	    public Map<String, Object> addComment(@PathVariable Integer discussId,
	                                              @RequestParam String content,
	                                              HttpSession session) {
	        UserCert userCert = (UserCert) session.getAttribute("userCert");

	        Discuss discuss = discussService.getDiscussEntityById(discussId)
	                .orElseThrow(() -> new DiscussException("DiscussDTO not found"));

	        if (content == null || content.trim().isEmpty()) {
	            throw new RuntimeException("留言內容不得為空");
	        }

	        if (content.length() > 100) {
	            throw new RuntimeException("留言內容不得超過 100 字");
	        }

	        CommentDTO  comment = commentService.addComment(content.trim(), userCert.getUsername(), discuss);

	        Map<String, Object> result = new HashMap<>();
	        result.put("comment", comment);
	        return result;
	    }
	    
	    @GetMapping("/chart-food-count")
	    @ResponseBody
	    public Map<String, Long> getFoodCountChartData(@RequestParam Integer discussId) {
	        return behaviorService.getFoodCountInLastMonth(discussId);
	    }


}


