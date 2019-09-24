package com.example.springcourse.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.dto.RequestSavedto;
import com.example.springcourse.dto.RequestUpdatedto;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.service.RequestService;
import com.example.springcourse.service.RequestStageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "requests")
@RequiredArgsConstructor
public class RequestResource {
	
	@Autowired private RequestService requestService;
	@Autowired private RequestStageService requestStageService;
	
	//save
	/*
	 * @PostMapping public ResponseEntity<Request> save(@RequestBody Request
	 * request){ Request createdRequest = requestService.save(request); return
	 * ResponseEntity.status(HttpStatus.CREATED).body(createdRequest); }
	 */
	@PostMapping
	public ResponseEntity<Request> save(@RequestBody @Valid RequestSavedto requestdto){
		Request request = requestdto.transformToRequest();
		Request createdRequest = requestService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}
	
	//update
	/*
	 * @PutMapping("/{id}") public ResponseEntity<Request> update(@PathVariable(name
	 * = "id") Long id, @RequestBody Request request){ request.setId(id); Request
	 * updatedRequest = requestService.update(request); return
	 * ResponseEntity.ok(updatedRequest); }
	 */
	@PreAuthorize("@accessManager.isRequestOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable(name = "id") Long id, @RequestBody @Valid RequestUpdatedto requestdto){
		Request request = requestdto.transformToRequest();
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
	/*
	 * @GetMapping public ResponseEntity<List<Request>> listAll(){ List<Request>
	 * requests = requestService.listAll(); return ResponseEntity.ok(requests);
	 * 
	 * }
	 */
	
	//list
	@GetMapping
	public ResponseEntity<PageModel<Request>> listAll(
			@RequestParam(value = "page", defaultValue = "0") int page, 
			@RequestParam(value = "size", defaultValue = "10") int size) {
		
		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<Request> pm = requestService.listAllOnLazyModel(pr);
		return ResponseEntity.ok(pm);
	} 
	
	/*
	 * @GetMapping("/{id}/request-stages") public ResponseEntity<List<RequestStage>>
	 * listAllStagesById(@PathVariable(name = "id") Long id){ List<RequestStage>
	 * stages = requestStageService.listAllByRequestId(id); return
	 * ResponseEntity.ok(stages); }
	 */
	
	@GetMapping("/{id}/request-stages")
	public ResponseEntity<PageModel<RequestStage>> listAllStagesById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size){
		
		PageRequestModel pr = new PageRequestModel(page, size);
		
		PageModel<RequestStage> pm = requestStageService.listAllByRequestIdOnLazyModel(id, pr);
		return ResponseEntity.ok(pm);
	}
	
	//lista all by owner id
	//http://localhost:8080/users/id/requests
	//vai ficar no userResource
}
