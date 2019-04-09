package com.example.springcourse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.User;
import com.example.springcourse.repository.UserRepository;
import com.example.springcourse.service.util.HashUtil;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	
	//save
	public User save(User user) {
		
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);
		
		User createdUser = userRepository.save(user);
		return createdUser;
	}
	
	
	//update
	public User update(User user) {
		
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);
		
		User updatedUser = userRepository.save(user);
		return updatedUser;
	}
	
	//get
	public User getById(Long id) {
		Optional<User> result = userRepository.findById(id);
		return result.get();
	}
	
	//list
	public List<User> listAll(){
		List<User> users = userRepository.findAll();
		return users;
	}
	
	//login
	public User login(String email, String password) {
		
		password = HashUtil.getSecureHash(password);
		
		Optional<User> result = userRepository.login(email, password);
		return result.get();
	}
}
