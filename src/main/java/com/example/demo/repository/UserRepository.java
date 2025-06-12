package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	// T-SQL, 注意:欄位名要符合資料表中的設定
	@Query(value = "select user_id, username, password_hash, salt, email, active, role from users where username=:username", nativeQuery = true)
	User getUser(String username); // 也可以用 findByUserName (有 3 種寫法, 寫其中一種就好)

	// 自動生成 SQL
	boolean existsByUsername(String username);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
}
