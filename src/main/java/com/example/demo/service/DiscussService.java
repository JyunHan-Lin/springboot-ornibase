package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.DiscussDTO;

public interface DiscussService {
	
	public DiscussDTO createDiscuss(DiscussDTO discussDTO);
	public List<DiscussDTO>getAllDiscuss();
	public Optional<DiscussDTO> getDiscussById(Integer discussId);
	public List<DiscussDTO> getDiscussByUserId(Integer id);
	public void updateDiscuss(Integer discussId, Integer userId, DiscussDTO discussDTO);
	public void updateDiscuss(Integer discussId, String title, String description, String tag, String youtubeVideoId, Boolean isPublic, LocalDateTime createdTime, Integer userId, String creatorName);
	public void deleteDiscuss(Integer discussId, Integer userId, DiscussDTO discussDTO);
	boolean hasUserFavorited(Integer userId, Integer discussId);
	void addFavorite(Integer userId, Integer discussId);

}
