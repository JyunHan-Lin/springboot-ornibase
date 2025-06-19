package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Discuss;

@Repository
public interface DiscussRepository extends JpaRepository<Discuss, Integer>{
	
	@Query("SELECT d FROM Discuss d JOIN FETCH d.user WHERE d.discussId = :discussId")
	Optional<Discuss> findByDiscussIdWithUser(Integer discussId);

	List<Discuss> findByUser_UserId(Integer userId); // JPA 是用資料庫名稱建立方法
	List<Discuss> findByIsPublicTrue();
	// 搜尋
	List<Discuss> findByIsPublicTrueAndTitleContainingIgnoreCaseOrIsPublicTrueAndDescriptionContainingIgnoreCaseOrIsPublicTrueAndTagIgnoreCase(
    
	String titleKeyword, String descriptionKeyword, String tagKeyword);

}
