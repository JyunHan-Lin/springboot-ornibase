package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Discuss;

@Repository
public interface DiscussRepository extends JpaRepository<Discuss, Integer>{
	List<Discuss> findByUser_UserId(Integer userId); // JPA 是用資料庫名稱建立方法
	
//	List<Discuss> findByTitle(String title); 搜尋功能待補###
//	List<Discuss> findByUser_UserIdOrderByCreatedTimeDesc(Integer userId); 依照使用者建立時間排序待補###

}
