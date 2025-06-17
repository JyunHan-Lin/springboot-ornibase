package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.BehaviorDTO;
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Behavior;
import com.example.demo.model.entity.Comment;

@Component
public class CommentMapper {
	
	@Autowired ModelMapper modelMapper;
	
	public CommentDTO toDTO(Comment comment) {
		// Entity 轉 DTO
		return modelMapper.map(comment, CommentDTO.class);
	}
	
	public Comment toEntity(CommentDTO commentDTO) {
		// DTO 轉 Entity
		return modelMapper.map(commentDTO, Comment.class);
	}
}
