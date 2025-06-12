package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.BehaviorDTO;

public interface BehaviorService {
	public void saveBehavior(Integer discussId, Integer userId, BehaviorDTO behaviorDTO);
//	public List<BehaviorDTO> getAllBehavior();
	public List<BehaviorDTO> getBehaviorsByDiscussAndUser(Integer discussId, Integer userId);
	public Optional<BehaviorDTO> getBehaviorById(Integer behaviorId);
	public void updateBehavior(Integer behaviorId, BehaviorDTO behaviorDTO);
	public void updateBehavior(Integer behaviorId, LocalDate date, LocalTime startTime, LocalTime endTime, String subject, String action, String food, Float temperature, String note);
	public void deleteBehavior(Integer behaviorId);
}
