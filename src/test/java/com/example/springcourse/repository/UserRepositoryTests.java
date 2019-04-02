package com.example.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.Role;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired private UserRepository userRepository;
	
	public void saveTest() {
		
		  User user = new User(null,"Kevin", "kevin.wingi@gmail.com", "123",  Role.ADMINISTRATOR, null, null);
		  User userCreated = userRepository.save(user);
		  
		  assertThat(userCreated.getId()).isEqualTo(1L);
		 
	}
	
	public void updateTest() {
		
		User user = new User(1L,"Kevin", "kevin.wingi@gmail.com", "123",  Role.ADMINISTRATOR, null, null);
		User userUpdated = userRepository.save(user);
		
		assertThat(userUpdated.getName()).isEqualTo("Kevin Wingi");
		
	}
	
	public void getByIdTest() {
		Optional<User> result = userRepository.findById(1L);
		
		User user = result.get();
		
		assertThat(user.getPassword()).isEqualTo("123");
	}
	
	public void listTest() {}
	
	public void loginTest() {}

}
