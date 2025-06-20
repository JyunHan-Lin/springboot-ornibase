package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Discuss;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public List<Comment> getCommentsByDiscuss(Discuss discuss) {
		return commentRepository.findByDiscussOrderByCreatedTimeAsc(discuss);
	}

	@Override
	public CommentDTO addComment(String content, String userName, Discuss discuss) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setUserName(userName);
		comment.setCreatedTime(LocalDateTime.now());
		comment.setDiscuss(discuss);
		Comment saved = commentRepository.save(comment);
		return commentMapper.toDTO(saved); 
	}

	@Override
	public List<CommentDTO> getCommentDTOs(Discuss discuss) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	    return commentRepository.findByDiscussOrderByCreatedTimeAsc(discuss)
						        .stream()
						        .map(commentMapper::toDTO)
						        .collect(Collectors.toList());
	}
}