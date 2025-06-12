package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.entity.Discuss;
import com.example.demo.model.entity.User;

@Component
public class DiscussMapper {
	
	@Autowired ModelMapper modelMapper;
	
	public DiscussDTO toDTO(Discuss discuss) {
		// Entity 轉 DTO
		return modelMapper.map(discuss, DiscussDTO.class);
	}
	
	public Discuss toEntity(DiscussDTO discussDTO) {
		// DTO 轉 Entity
		return modelMapper.map(discussDTO, Discuss.class);
	}
}
