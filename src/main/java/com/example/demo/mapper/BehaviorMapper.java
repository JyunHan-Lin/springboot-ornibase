package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.BehaviorDTO;
import com.example.demo.model.entity.Behavior;

@Component
public class BehaviorMapper {
	
	@Autowired ModelMapper modelMapper;
	
	public BehaviorDTO toDTO(Behavior behavior) {
		// Entity 轉 DTO
		return modelMapper.map(behavior, BehaviorDTO.class);
	}
	
	public Behavior toEntity(BehaviorDTO behaviorDTO) {
		// DTO 轉 Entity
		return modelMapper.map(behaviorDTO, Behavior.class);
	}
}
