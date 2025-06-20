package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.model.dto.BehaviorDTO;

public interface BehaviorService {
	public void saveBehavior(Integer discussId, Integer userId, BehaviorDTO behaviorDTO);
//	public List<BehaviorDTO> getAllBehavior();
//	public List<BehaviorDTO> getBehaviorsByDiscussAndUser(Integer discussId, Integer userId);
	public List<BehaviorDTO> getBehaviorByDiscussId(Integer discussId);
	public Optional<BehaviorDTO> getBehaviorById(Integer behaviorId);
	public void updateBehavior(Integer behaviorId, BehaviorDTO behaviorDTO);
	public void deleteBehavior(Integer behaviorId, Integer currentUserId);
	List<Map<String, Object>> getTimelineData(LocalDate date);
	public int countByDiscussId(Integer discussId);
	public List<BehaviorDTO> getBehaviorByDiscussIdAndDate(Integer discussId, LocalDate date);
	public Map<String, Long> getFoodCountInLastMonth(Integer discussId);
}
