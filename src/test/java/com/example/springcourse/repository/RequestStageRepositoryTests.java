package com.example.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.RequestState;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class RequestStageRepositoryTests {
	
	@Autowired private RequestStageRepository requestStageRepository;

	@Test
	public void saveTest() {
		User user = new User();
		user.setId(1L);
		
		Request request = new Request();
		request.setId(1L);
		
		RequestStage stage = new RequestStage(null, "Foi comprado um novo laptop", new Date(), RequestState.CLOSED, request, user); 
		
		RequestStage createdStage = requestStageRepository.save(stage);
		
		assertThat(createdStage.getId()).isEqualTo(1L);
	}

	@Test
	public void getByIdTest() {

		Optional<RequestStage> result =  requestStageRepository.findById(1L);

		RequestStage requestStage = result.get();
		assertThat(requestStage.getDescrition()).isEqualTo("Something");
		
	}

	@Test
	public void listByRequestIdTest() {
		List<RequestStage> stages = requestStageRepository.findAll();

		assertThat(stages.size()).isEqualTo(1);
	}
}
