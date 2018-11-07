package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDB;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDB userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}
	
	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
	
	@Override
	public UserRoleModel getUserByUsername(String username) {
		return userDb.findByUsername(username);
	}
	
	@Override
	public boolean cekPassword(String passwordLama, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(passwordLama, encodedPassword);
		
	}
}
