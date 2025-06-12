package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.entity.User;

@Component
public class UserMapper {
	
	@Autowired ModelMapper modelMapper;
	
	public UserDTO toDTO(User user) {
		// Entity 轉 DTO
		return modelMapper.map(user, UserDTO.class);
	}
	
	public User toEntity(UserDTO userDTO) {
		// DTO 轉 Entity
		return modelMapper.map(userDTO, User.class);
	}
}
