package com.example.springcourse.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.service.RequestStageService;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {
	
	@Autowired private RequestStageService requestStageService;
	
	//save
	@PostMapping
	public ResponseEntity<RequestStage> save(@RequestBody RequestStage requestStage){
		RequestStage createdRequestStage = requestStageService.save(requestStage);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestStage);
		
	}
	
	
	//get by id
	@GetMapping("/{id}")
	public ResponseEntity<RequestStage> getById(@PathVariable(name = "id") Long id){
		RequestStage stage = requestStageService.getById(id);
		return ResponseEntity.ok(stage);
	}
	

}
