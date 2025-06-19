package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DiscussCreateException;
import com.example.demo.exception.DiscussNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.DiscussMapper;
import com.example.demo.model.dto.DiscussDTO;
import com.example.demo.model.entity.Discuss;
import com.example.demo.model.entity.Favorite;
import com.example.demo.model.entity.User;
import com.example.demo.repository.BehaviorRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.DiscussRepository;
import com.example.demo.repository.FavoriteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DiscussService;

import jakarta.transaction.Transactional;

@Service
public class DiscussServiceImpl implements DiscussService{

	@Autowired
	private DiscussRepository discussRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private BehaviorRepository behaviorRepository;
	
	@Autowired
	private DiscussMapper discussMapper;
	
	// 建立討論串
	@Override
	public DiscussDTO createDiscuss(DiscussDTO discussDTO) {
		System.out.println("收到的 isPublic = " + discussDTO.getIsPublic());

		Discuss discuss = discussMapper.toEntity(discussDTO); // 將 DTO 轉成 Entity

	    if (discussDTO.getUserId() != null) {
	        User user = userRepository.findById(discussDTO.getUserId())
	                                  .orElseThrow(() -> new UserNotFoundException("找不到使用者"));
	        discuss.setUser(user);  // user 名下建立討論串
	    }
	    System.out.println("存入的 isPublic = " + discuss.getIsPublic());

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
					            .map(discuss -> {
					                DiscussDTO dto = discussMapper.toDTO(discuss);
					                // 用 userId 去 userRepository 查
					                User user = userRepository.findById(discuss.getUser().getUserId())
					                                          .orElseThrow(() -> new UserNotFoundException("找不到使用者"));
					                dto.setCreatorName(user.getUsername());
					                return dto;
					            });	
	}
	
	@Override
	public Optional<Discuss> getDiscussEntityById(Integer discussId) {
	    return discussRepository.findById(discussId);
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
		Discuss original = discussRepository.findByDiscussIdWithUser(discussId)
		.orElseThrow(() -> new DiscussNotFoundException("修改失敗: 討論串" + discussId + "不存在"));
		
		// 比對建立者
		if (!userId.equals(original.getUser().getUserId())) {
			throw new DiscussCreateException("修改失敗: " + userId + "不是建立者");
		}
		

	    // 更新可編輯欄位（不要動 user）
	    original.setTitle(discussDTO.getTitle());
	    original.setDescription(discussDTO.getDescription());
	    original.setYoutubeVideoId(discussDTO.getYoutubeVideoId());
	    original.setTag(discussDTO.getTag());
	    original.setIsPublic(discussDTO.getIsPublic());

	    discussRepository.saveAndFlush(original);
	}

	@Override
	public void updateDiscuss(Integer discussId, String title, String description, String tag, String youtubeVideoId, Boolean isPublic, LocalDateTime createdTime, Integer userId, String creatorName) {
		DiscussDTO discussDTO = new DiscussDTO(discussId, title, description, tag, youtubeVideoId, isPublic, createdTime, userId, creatorName);
		updateDiscuss(discussId, userId, discussDTO);		
	}

	
	// 刪除討論串
	@Override
	@Transactional
	public void deleteDiscuss(Integer discussId, Integer userId, DiscussDTO discussDTO) {
		// 判斷該討論串是否已存在?
		Discuss original = discussRepository.findByDiscussIdWithUser(discussId)
		.orElseThrow(() -> new DiscussNotFoundException("修改失敗: 討論串" + discussId + "不存在"));
		
		// 比對建立者
		if (!userId.equals(original.getUser().getUserId())) {
			throw new DiscussCreateException("修改失敗: " + userId + "不是建立者");
		}
		// 先刪留言
		commentRepository.deleteByDiscuss_DiscussId(discussId);
		// 再刪行為
		behaviorRepository.deleteByDiscuss_DiscussId(discussId);
		// 再刪收藏
		favoriteRepository.deleteByDiscuss_DiscussId(discussId);
		// 最後刪discuss
		discussRepository.deleteById(discussId);
	}

	@Override
	public boolean hasUserFavorited(Integer userId, Integer discussId) {
	    return favoriteRepository.existsByUser_UserIdAndDiscuss_DiscussId(userId, discussId);
	}

	@Override
	public void addFavorite(Integer userId, Integer discussId) {
		if (!hasUserFavorited(userId, discussId)) {
			Discuss discuss = discussRepository.findById(discussId)
					.orElseThrow(() -> new DiscussNotFoundException("討論串不存在"));
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException("使用者不存在"));
			Favorite favorite = new Favorite();
			favorite.setUser(user);
			favorite.setDiscuss(discuss);
			favoriteRepository.save(favorite);
		}
    }

	@Override
	@Transactional
	public void removeFavorite(Integer userId, Integer discussId) {
	    favoriteRepository.deleteByUser_UserIdAndDiscuss_DiscussId(userId, discussId);
	}

	@Override
	public List<DiscussDTO> getPublicDiscussList() {
		return discussRepository.findByIsPublicTrue()
								.stream()
								.map(discussMapper::toDTO)
								.toList();
	}

	@Override
	public List<DiscussDTO> getMyPrivateDiscuss(Integer userId) {
		return discussRepository.findByUser_UserId(userId)
								.stream()
								.map(discussMapper::toDTO)
								.toList();
	}

	@Override
	public List<DiscussDTO> getMyFavoritePublicDiscuss(Integer userId) {
		return favoriteRepository.findByUser_UserId(userId)
								 .stream()
								 .map(fav -> fav.getDiscuss())
								 .filter(discuss -> discuss.getIsPublic())
								 .map(discussMapper::toDTO)
								 .toList();
	}

	@Override
	public List<DiscussDTO> searchDiscusses(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            // 空關鍵字就回傳全部
            List<Discuss> allDiscusses = discussRepository.findAll();
            return allDiscusses.stream()
                    .map(discussMapper::toDTO)
                    .collect(Collectors.toList());
        }

        List<Discuss> discusses = discussRepository
            .findByIsPublicTrueAndTitleContainingIgnoreCaseOrIsPublicTrueAndDescriptionContainingIgnoreCaseOrIsPublicTrueAndTagIgnoreCase(keyword, keyword, keyword);

        return discusses.stream()
                .map(discussMapper::toDTO)
                .collect(Collectors.toList());
    }

	@Override
	public Map<Integer, Integer> getFavoriteCountMap() {
		List<Object[]> results = favoriteRepository.countFavoritesGroupByDiscussId();
		return results.stream()
				      .collect(Collectors.toMap(
				    		  row -> (Integer) row[0],
				    		  row -> ((Long) row[1]).intValue()	// JPA 查回來的格式就是Long
				    		  ));
	}
}
	



