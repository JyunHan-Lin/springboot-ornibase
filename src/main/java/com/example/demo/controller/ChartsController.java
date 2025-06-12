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
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.BehaviorDTO;
import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserCert;
import com.example.demo.service.BehaviorService;
import com.example.demo.service.DiscussService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bbd/discuss")
public class ChartsController {

	@Autowired
	private DiscussService discussService;
	
	@Autowired
	private BehaviorService behaviorService;
	
	// 建立後的頁面
		@GetMapping("/{discussId}")
		public String viewReport(@PathVariable Integer discussId, Model model, HttpSession session) {
		    DiscussDTO discussDTO = discussService.getDiscussById(discussId)
		    									  .orElseThrow(() -> new RuntimeException("DiscussDTO not found"));
		    // 取出登入者
		    UserCert userCert = (UserCert) session.getAttribute("userCert");
		    Integer userId = userCert != null ? userCert.getUserId() : null; // 預設 userId (測試環境)
		    
		    // 安全驗證：只允許建立者瀏覽
		    if (!userId.equals(discussDTO.getUserId())) {
		    	throw new RuntimeException("並非討論串建立者無法檢視討論串");
		    }
		    
		    model.addAttribute("discussDTO", discussDTO);

		    // 行為資料
		    List<BehaviorDTO> behaviors = behaviorService.getBehaviorsByDiscussAndUser(discussId, userId);

		    // google charts1 (對象1天行為活動)
		    // 先計算出行為的時間, 再算出佔一天多少比例
		    Map<String, Long> actionDuration = 
		    		behaviors.stream()
		    	    		 .filter(b -> b.getSubject().equals("幼鳥A") && b.getDate().equals(LocalDate.now()))
		    	    		 .collect(Collectors.groupingBy(BehaviorDTO::getAction,
		    	              Collectors.summingLong(b -> java.time.Duration.between(b.getStartTime(), b.getEndTime()).toMinutes())
		    	    ));

		    	    model.addAttribute("actionDuration", actionDuration);

		    // google charts2 (所有對象1天行為區間)
    	    List<List<Object>> timelineData = 
    	    		behaviors.stream()
    	    	    	     .filter(b -> b.getDate().equals(LocalDate.now())) // 只取某一天
    	    	    	     .map(b -> {
    	    	    	    	    List<Object> row = new java.util.ArrayList<>();
    	    	    	    	    row.add(b.getSubject() + "：" + b.getAction());
    	    	    	    	    row.add(null);
    	    	    	    	    row.add(LocalTime.of(b.getStartTime().getHour(), b.getStartTime().getMinute()));
    	    	    	    	    row.add(LocalTime.of(b.getEndTime().getHour(), b.getEndTime().getMinute()));
    	    	    	    	    return row;
    	    	    	    })
    	    	    	    .collect(Collectors.toList()); 
    	    		model.addAttribute("timelineData", timelineData);
    	    
    	    		/**
    	    		 * 產出來的List<List<Object>>會長這樣
    	    		 * [
  						 ["幼鳥A：覓食", null, LocalTime.of(8, 30), LocalTime.of(9, 0)],
  						 ["幼鳥B：睡覺", null, LocalTime.of(9, 15), LocalTime.of(10, 0)]
					   ]
					   這就是 Google Charts Timeline 的預設格式（Label, Row ID/null, Start Time, End Time）
    	    		 **/
    	    		
		    
		    // google charts3 (一週食物種類 + 數量)
    		Map<String, Long> foodCount = 
    				behaviors.stream()
    			    		 .filter(b -> b.getSubject().equals("幼鳥A")) // 指定對象
    			    		 .filter(b -> b.getDate().isAfter(LocalDate.now().minusDays(7)))
    			    		 .filter(b -> b.getFood() != null) // 避免為空值
    			    		 .collect(Collectors.groupingBy(BehaviorDTO::getFood, Collectors.counting()));

    				model.addAttribute("foodCount", foodCount);

		    
		    return "discuss/discuss"; // JSP頁面名稱
		}
}
