package com.example.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.enums.RequestState;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.repository.RequestRepository;
import com.example.springcourse.repository.RequestStageRepository;

@Service
public class RequestStageService {

	@Autowired private RequestStageRepository requestStageRepository; 
	@Autowired private RequestRepository requestRepository; 
	
	//save
	public RequestStage save(RequestStage stage) {
		
		stage.setRealizationDate(new Date());
		
		RequestStage createdStage = requestStageRepository.save(stage);
		
		Long requestId = stage.getRequest().getId();
		RequestState state = stage.getRequestState();
		
		requestRepository.updateStatus(requestId, state);
		return createdStage;
	}
	
	//get
	public RequestStage getById(Long id) {
		Optional<RequestStage> result = requestStageRepository.findById(id);
		return result.orElseThrow(()-> new NotFoundException("There are not request stage with id = "+ id));
	}
	
	//get by request id
	public List<RequestStage> listAllByRequestId(Long requestId){
		List<RequestStage> stages = requestStageRepository.findAllByRequestId(requestId);
		return stages;
	}
	
	public PageModel<RequestStage> listAllByRequestIdOnLazyModel(Long requestId, PageRequestModel pr){
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<RequestStage> page = requestStageRepository.findAllByRequestId(requestId, pageable);
		
		PageModel<RequestStage> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pm;
	}
	
}
