package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DiscussCreateException;
import com.example.demo.exception.DiscussNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.DiscussMapper;
import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.entity.Discuss;
import com.example.demo.model.entity.User;
import com.example.demo.repository.DiscussRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DiscussService;

@Service
public class DiscussServiceImpl implements DiscussService{

	@Autowired
	private DiscussRepository discussRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DiscussMapper discussMapper;
	
	// 建立討論串
	@Override
	public DiscussDTO createDiscuss(DiscussDTO discussDTO) {
	    Discuss discuss = discussMapper.toEntity(discussDTO); // 將 DTO 轉成 Entity

	    if (discussDTO.getUserId() != null) {
	        User user = userRepository.findById(discussDTO.getUserId())
	                                  .orElseThrow(() -> new UserNotFoundException("找不到使用者"));
	        discuss.setUser(user);  // user 名下建立討論串
	    }
	    
	    Discuss savedDiscuss = discussRepository.save(discuss); // 存進 DB
	    return discussMapper.toDTO(savedDiscuss); // 存完再轉成 DTO 回傳
	}

	// 查看所有討論串 (搜尋欄?)
	@Override
	public List<DiscussDTO> getAllDiscuss() {
	    List<Discuss> discusses = discussRepository.findAll();
	    return discusses.stream()
	            		.map(discussMapper::toDTO)
	            		.toList();
	}
	
	// 用討論串 ID 查詢單筆討論串 (討論串因為只顯示使用者自己的, 編輯及刪除就不考慮user)
	public Optional<DiscussDTO> getDiscussById(Integer discussId) {
	    return discussRepository.findById(discussId)
	                            .map(discussMapper::toDTO);
	}
	
	// 用使用者 ID 查詢該使用者的所有討論串 (只顯示使用者自己建立的討論串, 用在首頁清單上)
	@Override
	public List<DiscussDTO> getDiscussByUserId(Integer userId) {
	    List<Discuss> discusses = discussRepository.findByUser_UserId(userId);
	    return discusses.stream()
                		.map(discussMapper::toDTO)
                		.toList();
	}
	
	
	// 編輯討論串
	@Override
	public void updateDiscuss(Integer discussId, Integer userId, DiscussDTO discussDTO) {
		// 判斷該討論串是否已存在?
		Optional<Discuss> optDiscuss = discussRepository.findById(discussId);
		if (optDiscuss.isEmpty()) {
			throw new DiscussNotFoundException("修改失敗: 討論串" + discussId + "不存在");
		}
		// 判斷是否是討論串建立者
		if (!userId.equals(discussDTO.getUserId())) {
			throw new DiscussCreateException("修改失敗: " + userId + "不是建立者");
		}
		
	    Discuss original = optDiscuss.get(); // 原本的 Discuss 實體

	    // 更新可編輯欄位（不要動 user）
	    original.setTitle(discussDTO.getTitle());
	    original.setDescription(discussDTO.getDescription());
	    original.setYoutubeVideoId(discussDTO.getYoutubeVideoId());

	    discussRepository.saveAndFlush(original);
	}

	@Override
	public void updateDiscuss(Integer discussId, Integer userId, String title, String description, String youtubeVideoId, LocalDateTime createdTime) {
		DiscussDTO discussDTO = new DiscussDTO(discussId, title, description, youtubeVideoId, createdTime, userId);
		updateDiscuss(discussId, userId, discussDTO);		
	}

	
	// 刪除討論串
	@Override
	public void deleteDiscuss(Integer discussId, Integer userId, DiscussDTO discussDTO) {
		Optional<Discuss> optDiscuss = discussRepository.findById(discussId);

		// 判斷該討論串是否已存在?
		if (optDiscuss.isEmpty()) {
			throw new DiscussNotFoundException("刪除失敗: 討論串" + discussId + "不存在");
		}
		// 判斷是否是討論串建立者
		if (!userId.equals(discussDTO.getUserId())) {
			throw new DiscussCreateException("刪除失敗: " + userId + "不是建立者");
		}
		discussRepository.deleteById(discussId);
	}
}
