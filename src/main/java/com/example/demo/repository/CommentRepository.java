package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Discuss;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment>findByDiscussOrderByCreatedTimeAsc(Discuss discuss);
	void deleteByDiscuss_DiscussId(Integer discussId);

}
