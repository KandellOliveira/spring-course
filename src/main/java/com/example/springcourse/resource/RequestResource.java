package com.example.springcourse.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.service.RequestService;
import com.example.springcourse.service.RequestStageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "requests")
@RequiredArgsConstructor
public class RequestResource {
	
	private RequestService requestService;
	private RequestStageService requestStageService;
	
	//save
	@PostMapping
	public ResponseEntity<Request> save(@RequestBody Request request){
		Request createdRequest = requestService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}
	
	//update
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable(name = "id") Long id, @RequestBody Request request){
		request.setId(id);
		Request updatedRequest = requestService.update(request);
		return ResponseEntity.ok(updatedRequest);
	}
	
	//getById
	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable(name = "id") Long id){
		Request request = requestService.getById(id);
		return ResponseEntity.ok(request);
	}
	
	//list all
	@GetMapping
	public ResponseEntity<List<Request>> listAll(){
		List<Request> requests = requestService.listAll();
		return ResponseEntity.ok(requests);
		
	}
	
	@GetMapping("/{id}/request-stages")
	public ResponseEntity<List<RequestStage>> listAllStagesById(@PathVariable(name = "id") Long id){
		List<RequestStage> stages = requestStageService.listAllByRequestId(id);
		return ResponseEntity.ok(stages);
	}
	
	//lista all by owner id
	//http://localhost:8080/users/id/requests
	//vai ficar no userResource
}
