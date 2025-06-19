package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/preview")
public class PreviewController {
	
	@Autowired
	private DiscussService discussService;
	
	@Autowired
	private BehaviorService behaviorService;
	
	@GetMapping
	public String previewPage(Model model) {
		// 看得到大家建立的記錄本
	    // 取得公開的討論串列表
	    List<DiscussDTO> discussList = discussService.getPublicDiscussList();
	    
	    // 用 discussId 拿行為筆數
	    Map<Integer, Integer> behaviorCountMap = new HashMap<>();
	    for (DiscussDTO discuss : discussList) {
	        int count = behaviorService.countByDiscussId(discuss.getDiscussId());
	        behaviorCountMap.put(discuss.getDiscussId(), count);
	    }
	    
	    Map<Integer, Integer> favoriteCountMap = discussService.getFavoriteCountMap();
	    model.addAttribute("favoriteCountMap", favoriteCountMap);
	    
	    model.addAttribute("discussList", discussList);
	    model.addAttribute("behaviorCountMap", behaviorCountMap);
	    return "preview";
	}
	
    @GetMapping("/search")
    public String searchDiscusses(@RequestParam(required = false) String keyword, Model model) {
        List<DiscussDTO> discussList = discussService.searchDiscusses(keyword);
        
        Map<Integer, Integer> behaviorCountMap = new HashMap<>();
        for (DiscussDTO discuss : discussList) {
            int count = behaviorService.countByDiscussId(discuss.getDiscussId());
            behaviorCountMap.put(discuss.getDiscussId(), count);
        }
        
	    Map<Integer, Integer> favoriteCountMap = discussService.getFavoriteCountMap();
	    model.addAttribute("favoriteCountMap", favoriteCountMap);
	    
        model.addAttribute("discussList", discussList);
        model.addAttribute("behaviorCountMap", behaviorCountMap);
        model.addAttribute("keyword", keyword);
        return "preview";  // JSP 頁面路徑，自己調整
    }
}
