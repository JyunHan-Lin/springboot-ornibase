package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.CommentDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Discuss;

public interface CommentService {

	public List<Comment> getCommentsByDiscuss(Discuss discuss);
	public CommentDTO addComment(String content, String userName, Discuss discuss);
	public List<CommentDTO> getCommentDTOs(Discuss discuss);
}
