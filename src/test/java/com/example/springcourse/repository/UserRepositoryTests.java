package com.example.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.Role;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired private UserRepository userRepository;
	
	@Test
	public void AsaveTest() {
		
		  User user = new User(null,"Kevin", "kevin.wingi@gmail.com", "123",  Role.ADMINISTRATOR, null, null);
		  User userCreated = userRepository.save(user);
		  
		  assertThat(userCreated.getId()).isEqualTo(1L);
		 
	}
	
	@Test
	public void updateTest() {
		
		User user = new User(1L,"Kevin", "kevin.wingi@gmail.com", "123",  Role.ADMINISTRATOR, null, null);
		User userUpdated = userRepository.save(user);
		
		assertThat(userUpdated.getName()).isEqualTo("Kevin");
		
	}
	
	@Test
	public void getByIdTest() {
		Optional<User> result = userRepository.findById(1L);
		
		User user = result.get();
		
		assertThat(user.getPassword()).isEqualTo("123");
	}
	
	@Test
	public void listTest() {
		List<User> users = userRepository.findAll();
		
		assertThat(users.size()).isEqualTo(1);
		
	}
	
	@Test
	public void loginTest() {
		Optional<User> result = userRepository.login("kevin.wingi@gmail.com", "123");
		
		User loggerUser = result.get();
		
		assertThat(loggerUser.getId()).isEqualTo(1);
	}

	@Test
	public void updateRoleTest() {
		int affectedRows = userRepository.updateRole(4L, Role.ADMINISTRATOR);
		assertThat(affectedRows).isEqualTo(1);
	}
}
