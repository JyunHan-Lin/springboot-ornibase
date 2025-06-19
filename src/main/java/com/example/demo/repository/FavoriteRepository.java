package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{

	boolean existsByUser_UserIdAndDiscuss_DiscussId(Integer userId, Integer discuss_id);
	
	List<Favorite> findByUser_UserId(Integer userId);
	
	void deleteByDiscuss_DiscussId(Integer discussId);
	
	void deleteByUser_UserIdAndDiscuss_DiscussId(Integer userId, Integer discussId);

	// FavoriteRepository.java
	@Query("SELECT f.discuss.discussId, COUNT(f) FROM Favorite f GROUP BY f.discuss.discussId")
	List<Object[]> countFavoritesGroupByDiscussId();

}
