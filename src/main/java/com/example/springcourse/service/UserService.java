package com.example.springcourse.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.User;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.repository.UserRepository;
import com.example.springcourse.service.util.HashUtil;

@Service
public class UserService implements UserDetailsService {

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
		return result.orElseThrow(()-> new NotFoundException("There are not user whit id = "+ id));
	}
	
	//list
	public List<User> listAll(){
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public PageModel<User> listAllOnLazyModel(PageRequestModel pr){
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<User> page = userRepository.findAll(pageable);
		PageModel<User> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		return pm;
		
	}
	
	//login
	public User login(String email, String password) {
		
		password = HashUtil.getSecureHash(password);
		
		Optional<User> result = userRepository.login(email, password);
		return result.get();
	}
	
	public int updateRole(User user) {
		return userRepository.updateRole(user.getId(), user.getRole());
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = userRepository.findByEmail(username);
		
		if(!result.isPresent()) {
			throw new UsernameNotFoundException("Dosen't exist user whit email = "+username);
		}
		
		User user = result.get();
		
		List<GrantedAuthority> authAuthorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
		
		org.springframework.security.core.userdetails.User userSpring = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authAuthorities);
		
		return userSpring;
	}
	
}
