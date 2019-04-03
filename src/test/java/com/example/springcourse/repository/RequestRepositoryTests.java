package com.example.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.RequestState;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestRepositoryTests {

	@Autowired private RequestRepository requestRepository;
	
	public void saveTest() {
		
		User owner = new User();
		owner.setId(1L);
		
		Request request = new Request(null, "Novo Laptop HP", "Pretendo ob", new Date(), RequestState.OPEN, owner, null);
		
		Request createdRequest = requestRepository.save(request);
		
		assertThat(createdRequest.getId()).isEqualTo(1L);
		
	}
	
	public void updateTest() {
		
		User owner = new User();
		owner.setId(1L);
		
		Request request = new Request(1L, "Novo Laptop HP Alterei", "Pretendo ob Alterei", null, RequestState.OPEN, owner, null);
		
		Request updatedRequest = requestRepository.save(request);
		
		assertThat(updatedRequest.getDescription()).isEqualTo("Pretendo ob Alterei");
		
	}
	
	public void getByIdTest() {
		
		
	}
	
	public void listTest() {}
	
	
}
