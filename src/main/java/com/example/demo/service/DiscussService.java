package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.entity.Discuss;

public interface DiscussService {
	
	public DiscussDTO createDiscuss(DiscussDTO discussDTO);
	public List<DiscussDTO>getAllDiscuss();
	public Optional<DiscussDTO> getDiscussById(Integer discussId);
	public Optional<Discuss> getDiscussEntityById(Integer discussId);
	public List<DiscussDTO> getDiscussByUserId(Integer id);
	public void updateDiscuss(Integer discussId, Integer userId, DiscussDTO discussDTO);
	public void updateDiscuss(Integer discussId, String title, String description, String tag, String youtubeVideoId, Boolean isPublic, LocalDateTime createdTime, Integer userId, String creatorName);
	public void deleteDiscuss(Integer discussId, Integer userId, DiscussDTO discussDTO);
	boolean hasUserFavorited(Integer userId, Integer discussId);
	void addFavorite(Integer userId, Integer discussId);
	// 取得所有公開討論串
	List<DiscussDTO> getPublicDiscussList();
	// 取得未公開(私人)討論串
	List<DiscussDTO> getMyPrivateDiscuss(Integer userId);
	// 取得收藏的公開討論串
	List<DiscussDTO> getMyFavoritePublicDiscuss(Integer userId);
	List<DiscussDTO> searchDiscusses(String keyword);
	void removeFavorite(Integer userId, Integer discussId);
	Map<Integer, Integer> getFavoriteCountMap();
}
