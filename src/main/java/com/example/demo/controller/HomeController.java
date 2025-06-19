package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.BehaviorService;
import com.example.demo.service.DiscussService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ornibase")
public class HomeController {

	@Autowired
	private DiscussService discussService;
	
	@Autowired
	private BehaviorService behaviorService;
	
	@GetMapping
	public String mainPage(Model model, HttpSession session) {
		// 看得到大家建立的記錄本
	    // 取得公開的討論串列表
	    List<DiscussDTO> discussList = discussService.getPublicDiscussList();
	    
	    // 用 discussId 拿行為筆數
	    Map<Integer, Integer> behaviorCountMap = new HashMap<>();
	    for (DiscussDTO discuss : discussList) {
	        int count = behaviorService.countByDiscussId(discuss.getDiscussId());
	        behaviorCountMap.put(discuss.getDiscussId(), count);
	    }
	    // 假設在 Controller
	    // 取得目前登入使用者 ID
	    Integer userId = (Integer) session.getAttribute("userId");
	
	    // 取得使用者收藏的討論串 ID 清單
	    List<DiscussDTO> favoriteDiscusses = discussService.getMyFavoritePublicDiscuss(userId);
	    Set<Integer> favoriteDiscussIds = favoriteDiscusses.stream()
	                                         .map(DiscussDTO::getDiscussId)
	                                         .collect(Collectors.toSet());
	    
	    UserCert userCert = (UserCert) session.getAttribute("userCert");

	    Map<Integer, Integer> favoriteCountMap = discussService.getFavoriteCountMap();
	    model.addAttribute("favoriteCountMap", favoriteCountMap);

	 	// 傳給 JSP
	    model.addAttribute("userCert", userCert); 
	    model.addAttribute("discussList", discussList);
	    model.addAttribute("behaviorCountMap", behaviorCountMap);
	    model.addAttribute("favoriteDiscussIds", favoriteDiscussIds);
	    return "main";
	}
	
    @GetMapping("/search")
    public String searchDiscusses(@RequestParam(required = false) String keyword, Model model, HttpSession session) {
        List<DiscussDTO> discussList = discussService.searchDiscusses(keyword);
        
        Map<Integer, Integer> behaviorCountMap = new HashMap<>();
        for (DiscussDTO discuss : discussList) {
            int count = behaviorService.countByDiscussId(discuss.getDiscussId());
            behaviorCountMap.put(discuss.getDiscussId(), count);
        }
        // 顯示收藏人數
        Integer userId = (Integer) session.getAttribute("userId");
        List<DiscussDTO> favoriteDiscusses = discussService.getMyFavoritePublicDiscuss(userId);
        Set<Integer> favoriteDiscussIds = favoriteDiscusses.stream()
                                         .map(DiscussDTO::getDiscussId)
                                         .collect(Collectors.toSet());

        Map<Integer, Integer> favoriteCountMap = discussService.getFavoriteCountMap();
        
        model.addAttribute("discussList", discussList);
        model.addAttribute("behaviorCountMap", behaviorCountMap);
        model.addAttribute("favoriteCountMap", favoriteCountMap);
        model.addAttribute("favoriteDiscussIds", favoriteDiscussIds);
        model.addAttribute("keyword", keyword);
        return "main";  // JSP 頁面路徑，自己調整
    }
	
	// 登出
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login"; 
	}
	
}
