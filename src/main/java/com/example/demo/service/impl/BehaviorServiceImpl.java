package com.example.demo.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BehaviorNotFoundException;
import com.example.demo.mapper.BehaviorMapper;
import com.example.demo.model.dto.BehaviorDTO;
import com.example.demo.model.dto.DiscussDTO;
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
	private BehaviorMapper behaviorMapper;
	
	// 新增行為
	@Override
    public void saveBehavior(Integer discussId, Integer userId, BehaviorDTO behaviorDTO) {
        Optional<Discuss> discussOpt = discussRepository.findById(discussId);
        if (discussOpt.isPresent()) {
            Discuss discuss = discussOpt.get();

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
        behavior.setUser(discuss.getUser()); // 連接user (只看得到自己建立的行為)
        
        behaviorRepository.save(behavior);
        }
    }
    
	// 查詢userId、disscussId 建立的行為(只看得到自己建立的行為)
	@Override
	public List<BehaviorDTO> getBehaviorsByDiscussAndUser(Integer discussId, Integer userId) {
        return behaviorRepository.findByDiscussIdAndUserId(discussId, userId)
                                 .stream()
                        		 .map(behaviorMapper::toDTO)
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
		Optional<Behavior> optBehavior = behaviorRepository.findById(behaviorId);
		if (optBehavior.isEmpty()) {
			throw new BehaviorNotFoundException("修改失敗: 行為" + behaviorDTO.getBehaviorId() + "不存在");
		}
		Behavior original = optBehavior.get(); // 原本的 Discuss 實體

	    // 更新可編輯欄位（不要動 user）
	    original.setDate(behaviorDTO.getDate());
	    original.setStartTime(behaviorDTO.getStartTime());
	    original.setEndTime(behaviorDTO.getEndTime());
	    original.setSubject(behaviorDTO.getSubject());
	    original.setAction(behaviorDTO.getAction());
	    original.setTemperature(behaviorDTO.getTemperature());	    
	    original.setNote(behaviorDTO.getNote());

	    behaviorRepository.saveAndFlush(original);
	}

	@Override
	public void updateBehavior(Integer behaviorId, LocalDate date, LocalTime startTime, LocalTime endTime, String subject, String action, String food, Float temperature, String note) {
		BehaviorDTO behaviorDTO = new BehaviorDTO(behaviorId, date, startTime, endTime, subject, action, food, temperature, note, null, null);
		updateBehavior(behaviorId, behaviorDTO);		
	}

	@Override
	public void deleteBehavior(Integer behaviorId) {
		// 判斷該行為是否已存在?
		Optional<Behavior> optBehavior = behaviorRepository.findById(behaviorId);
		if (optBehavior.isEmpty()) {
			throw new BehaviorNotFoundException("刪除失敗: 行為" + behaviorId + "不存在");
		}
		behaviorRepository.deleteById(behaviorId);
	}
}

