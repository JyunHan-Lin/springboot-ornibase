package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.CertException;
import com.example.demo.exception.MailInvalidException;
import com.example.demo.exception.PasswordInvalidException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.UserCert;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CertService;
import com.example.demo.util.HashUtil;

@Service
public class CertServiceImpl implements CertService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	//public UserCert getCert(String username, String password) throws CertException {
	public UserCert getCert(String username, String password, String authCode, String sessionAuthCode) throws UserNotFoundException, PasswordInvalidException {
		// 1. 比對驗證碼
		if(!authCode.equals(sessionAuthCode)) {
			throw new CertException("驗證碼不符");
		}
		
		// 2. 是否有此人
		User user = userRepository.getUser(username);
		if(user == null) {
			throw new UserNotFoundException("查無此人");
		}
		
		// 3. 密碼 hash 比對
		String passwordHash = HashUtil.getHash(password, user.getSalt());
		if(!passwordHash.equals(user.getPasswordHash())) {
			throw new PasswordInvalidException("密碼錯誤");
		}
		
	    // 4.email驗證
	    if (!user.getActive()) {
	        throw new MailInvalidException("帳號尚未啟用，請先完成 Email 驗證");
	    }
	    
		// 5. 簽發憑證
		UserCert userCert = new UserCert(user.getUserId(), user.getUsername(), user.getRole());
		return userCert;
	}
}

