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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.BehaviorService;
import com.example.demo.service.DiscussService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/ornibase/discuss")
public class DiscussController {
	
	@Autowired
	private DiscussService discussService;
	
	@Autowired
	private BehaviorService behaviorService;
	
	// 檢視筆記本
	@GetMapping("/list")
	public String myDiscussList(Model model, HttpSession session) {
	    Integer userId = ((UserCert) session.getAttribute("userCert")).getUserId();

	    List<DiscussDTO> myDiscussList = discussService.getMyPrivateDiscuss(userId);
	    List<DiscussDTO> favoriteDiscussList = discussService.getMyFavoritePublicDiscuss(userId);

	    // 用 Map 儲存討論串ID對應的行為數
	    Map<Integer, Integer> behaviorCountMap = new HashMap<>();
	    for (DiscussDTO discuss : myDiscussList) {
	        int count = behaviorService.countByDiscussId(discuss.getDiscussId());
	        behaviorCountMap.put(discuss.getDiscussId(), count);
	    }
	    for (DiscussDTO discuss : favoriteDiscussList) {
	        int count = behaviorService.countByDiscussId(discuss.getDiscussId());
	        behaviorCountMap.put(discuss.getDiscussId(), count);
	    }
	    
	    Map<Integer, Integer> favoriteCountMap = discussService.getFavoriteCountMap();
	    model.addAttribute("favoriteCountMap", favoriteCountMap);
	    
	    model.addAttribute("myDiscussList", discussService.getMyPrivateDiscuss(userId));
	    model.addAttribute("favoriteDiscussList", discussService.getMyFavoritePublicDiscuss(userId));
	    model.addAttribute("behaviorCountMap", behaviorCountMap);

	    return "discuss/discuss-list";
	}
	
	// 建立筆記本的頁面
	@GetMapping("/new")
	public String createDiscuss(Model model) {
		model.addAttribute("discussDTO", new DiscussDTO());
		return "discuss/discuss-new";
	}
	
	// 建立筆記本
	@PostMapping("/new")
	public String saveDiscuss(@ModelAttribute DiscussDTO discussDTO, HttpSession session) {
		UserCert userCert = (UserCert) session.getAttribute("userCert");
		// 將 userId 放入 DTO
	    discussDTO.setUserId(userCert.getUserId());
	    System.out.println("收到 DiscussDTO.isPublic = " + discussDTO.getIsPublic());

		DiscussDTO savedDiscuss = discussService.createDiscuss(discussDTO);
	    return "redirect:/ornibase/discuss/" + savedDiscuss.getDiscussId();
	}
	
	// 編輯筆記本(標題、描述、網址: 點選到裡面再編輯) 
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

	
	// 刪除筆記本
	@DeleteMapping("/delete/{discussId}")
	public String deleteRoom(@PathVariable Integer discussId,  DiscussDTO discussDTO, HttpSession session) {
	    UserCert userCert = (UserCert) session.getAttribute("userCert");
	    Integer userId = userCert.getUserId();
		discussService.deleteDiscuss(discussId, userId, discussDTO);
		return "redirect:/ornibase/discuss/list"; // 重導到個人書架
	}
	
	@PostMapping("/favorite/{discussId}")
	public String favoriteDiscuss(@PathVariable Integer discussId, HttpSession session) {
	    UserCert userCert = (UserCert) session.getAttribute("userCert");
	    if (userCert == null) {
	        throw new RuntimeException("請先登入以收藏");
	    }
	    discussService.addFavorite(userCert.getUserId(), discussId);
	    return "redirect:/ornibase/discuss/" + discussId;
	}

	@PostMapping("/favorite/{discussId}/delete")
	public String deleteFavorite(@PathVariable Integer discussId, HttpSession session) {
	    UserCert userCert = (UserCert) session.getAttribute("userCert");
	    if (userCert == null) {
	        throw new RuntimeException("請先登入以收藏");
	    }
	    discussService.removeFavorite(userCert.getUserId(), discussId);
	    return "redirect:/ornibase";
	}
}
