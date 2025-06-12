package com.example.demo.service;

public interface UserService {
	public boolean addUser(String username, String password, String confirmPassword, String email, Boolean active, String role);
	public boolean confirmEmail(String username);
	public boolean changePassword(String email, String oldPassword, String newPassword, String confirmPassword);
}