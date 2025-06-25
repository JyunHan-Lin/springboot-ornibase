package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BehaviorException;
import com.example.demo.exception.BehaviorNotFoundException;
import com.example.demo.mapper.BehaviorMapper;
import com.example.demo.model.dto.BehaviorDTO;
import com.example.demo.model.entity.Behavior;
import com.example.demo.model.entity.Discuss;
import com.example.demo.model.entity.User;
import com.example.demo.repository.BehaviorRepository;
import com.example.demo.repository.DiscussRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BehaviorService;

@Service
public class BehaviorServiceImpl implements BehaviorService{

	@Autowired
	private BehaviorRepository behaviorRepository;
	
	@Autowired
	private DiscussRepository discussRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BehaviorMapper behaviorMapper;
	
	// 新增行為
	@Override
    public void saveBehavior(Integer discussId, Integer userId, BehaviorDTO behaviorDTO) {
        Optional<Discuss> discussOpt = discussRepository.findById(discussId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (discussOpt.isPresent() && userOpt.isPresent()) {
            Discuss discuss = discussOpt.get();
            User user = userOpt.get();

	        Behavior behavior = new Behavior();
	        behavior.setDate(behaviorDTO.getDate());
	        behavior.setStartTime(behaviorDTO.getStartTime());
	        behavior.setEndTime(behaviorDTO.getEndTime());
	        behavior.setSubject(behaviorDTO.getSubject());
	        behavior.setAction(behaviorDTO.getAction());
	        behavior.setFood(behaviorDTO.getFood());
	
	        behavior.setTemperature(behaviorDTO.getTemperature());
	        behavior.setNote(behaviorDTO.getNote());
	
	        behavior.setDiscuss(discuss); // 關聯討論串
	        behavior.setUser(user);
	        
	        behaviorRepository.save(behavior);
        }
    }
    
	// 查詢userId、disscussId 建立的行為(只看得到自己建立的行為)
//	@Override
//	public List<BehaviorDTO> getBehaviorsByDiscussAndUser(Integer discussId, Integer userId) {
//        return behaviorRepository.findByDiscussIdAndUserId(discussId, userId)
//                                 .stream()
//                        		 .map(behaviorMapper::toDTO)
//        	            		 .toList();
//    }
	// 查詢userId、disscussId 建立的行為(只看得到自己建立的行為)
	@Override
	public List<BehaviorDTO> getBehaviorByDiscussId(Integer discussId) {
        return behaviorRepository.findByDiscuss_DiscussIdFetchUser(discussId)
                                 .stream()
                                 .map(b -> {
                                     BehaviorDTO dto = behaviorMapper.toDTO(b);
                                     dto.setCreatorName(b.getUser() != null ? b.getUser().getUsername() : null);
                                     return dto;
                                 })
                                 .toList();
    }
	
	// 用行為 ID 查詢單筆討論串
	public Optional<BehaviorDTO> getBehaviorById(Integer behaviorId) {
	    return behaviorRepository.findById(behaviorId)
	                             .map(behaviorMapper::toDTO);
	}
	
	// 編輯行為
	@Override
	public void updateBehavior(Integer behaviorId, BehaviorDTO behaviorDTO) {
		// 判斷該房號是否已存在?
		Behavior behavior = behaviorRepository.findById(behaviorId)
				.orElseThrow(() -> new BehaviorNotFoundException("修改失敗: 行為不存在"));
		// 權限驗證: 只能改自己建立的行為
		if (!behavior.getUser().getUserId().equals(behaviorDTO.getUserId())) {
			throw new BehaviorException("不可編輯其他使用者記錄的行為");
		}

	    // 更新可編輯欄位（不要動 user）
		behavior.setDate(behaviorDTO.getDate());
	    behavior.setStartTime(behaviorDTO.getStartTime());
	    behavior.setEndTime(behaviorDTO.getEndTime());
	    behavior.setSubject(behaviorDTO.getSubject());
	    behavior.setAction(behaviorDTO.getAction());
	    behavior.setFood(behaviorDTO.getFood());
	    behavior.setTemperature(behaviorDTO.getTemperature());	    
	    behavior.setNote(behaviorDTO.getNote());
	    
	    behaviorRepository.saveAndFlush(behavior);
	}

	@Override
	public void deleteBehavior(Integer behaviorId, Integer currentUserId) {
	    Behavior behavior = behaviorRepository.findById(behaviorId)
	            .orElseThrow(() -> new BehaviorNotFoundException("刪除失敗: 行為" + behaviorId + "不存在"));

        if (!behavior.getUser().getUserId().equals(currentUserId)) {
            throw new RuntimeException("不可刪除其他使用者記錄的行為");
        }
		behaviorRepository.deleteById(behaviorId);
	}
	
	// 圖表1
	@Override
	public List<Map<String, Object>> getTimelineData(LocalDate date) {
	    List<Behavior> behaviors = behaviorRepository.findByDate(date);
	    return behaviors.stream().map(b -> {
	        Map<String, Object> row = new HashMap<>();
	        row.put("subject", b.getSubject());
	        row.put("action", b.getAction());
	        row.put("start", LocalDateTime.of(date, b.getStartTime()));
	        row.put("end", LocalDateTime.of(date, b.getEndTime()));
	        return row;
	    }).toList();
	}

	@Override
	public int countByDiscussId(Integer discussId) {
	    return behaviorRepository.countByDiscuss_DiscussId(discussId);
	}

	@Override
	public List<BehaviorDTO> getBehaviorByDiscussIdAndDate(Integer discussId, LocalDate date) {
	    List<Behavior> list = behaviorRepository.findByDiscuss_DiscussId(discussId)
	    		   .stream()
	               .filter(b -> b.getDate() != null && b.getDate().equals(date))
	               .collect(Collectors.toList());
	    return list
	    		.stream()
	    		.map(behaviorMapper::toDTO)
	    		.collect(Collectors.toList());
	}

	@Override
	public Map<String, Long> getFoodCountInLastMonth(Integer discussId) {
	    LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

	    List<Behavior> recentBehaviors = behaviorRepository
	        .findByDiscuss_DiscussIdAndDateAfter(discussId, oneMonthAgo);

	    return recentBehaviors.stream()
	        .filter(b -> b.getFood() != null && !b.getFood().isBlank())
	        .collect(Collectors.groupingBy(
	            Behavior::getFood,
	            Collectors.counting()
	        ));
	}
	
	

}

