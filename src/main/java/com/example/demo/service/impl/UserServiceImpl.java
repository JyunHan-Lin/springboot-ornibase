package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.PasswordInvalidException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;

	// 註冊
	// 參數待改 ?
	@Override
	public boolean addUser(String username, String password, String confirmPassword, String email, Boolean active, String role){
	    // 判斷使用者是否已存在
	    if (userRepository.existsByUsername(username)) {
	    	throw new UserAlreadyExistException("使用者註冊過了");
	    }
	    if (!password.equals(confirmPassword)) {
	    	throw new PasswordInvalidException("密碼輸入錯誤");
	    }
	    
	    // 加鹽
		String salt = HashUtil.getSalt();
		// 加鹽加密
		String passwordHash = HashUtil.getHash(password, salt);
		// 建立user物件
		User user = new User(null, username, passwordHash, salt, email, active, role, null);
		// 儲存到資料庫
		userRepository.save(user);
		System.out.println("使用者註冊成功");
		return true;
		
	}
	
	// email認證
	public boolean confirmEmail(String username) {
	    Optional<User> optUser = userRepository.findByUsername(username);
	    
	    if (optUser.isPresent()) {
	        User user = optUser.get();
	        // 假設 User 類別有一個表示認證狀態的欄位，例如 active
	        user.setActive(true);  // 將認證狀態設為 true (已認證)
	        userRepository.save(user); // 儲存更新後的使用者
	        return true; // 成功
	    } else {
	        return false; // 失敗
	    }
	}
	
	// 變更密碼
	public boolean changePassword(String username, String oldPassword, String newPassword, String confirmPassword) {
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) 
        	return false; // 找不到使用者

        User user = optUser.get();
        // 檢查舊密碼是否正確
        String passwordHash = HashUtil.getHash(oldPassword, user.getSalt());
		if(!passwordHash.equals(user.getPasswordHash())) {
			throw new PasswordInvalidException("原本密碼輸入錯誤");
		}
		
		// 檢查新密碼是否與舊密碼重複
		String confirmedPassword = HashUtil.getHash(newPassword, user.getSalt());
        if (confirmedPassword.equals(passwordHash)) {
    		throw new PasswordInvalidException("新密碼不可與舊密碼重複");
        }
        
        // 檢查新密碼與確認是否相同
		String checkedPassword = HashUtil.getHash(confirmPassword, user.getSalt());
        if (!checkedPassword.equals(confirmedPassword)) {
			throw new PasswordInvalidException("新密碼輸入錯誤");
        }

        user.setPasswordHash(checkedPassword);
        userRepository.save(user);
        return true;
	}
}