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
import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.RequestState;
import com.example.springcourse.exception.NotFoundException;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.repository.RequestRepository;

@Service
public class RequestService {

	@Autowired private RequestRepository requestRepository;
	
	//save
	public Request save(Request request) {
		
		request.setRequestState(RequestState.OPEN);
		request.setCreationDate(new Date());
		Request createdRequest = requestRepository.save(request);
		return createdRequest;
	}
	
	//update
	public Request update(Request request) {
		Request updatedRequest = requestRepository.save(request);
		return updatedRequest;
	}
	
	//get
	public Request getById(Long id) {
		Optional<Request> result = requestRepository.findById(id);
		return result.orElseThrow(()-> new NotFoundException("There are not request with id = "+ id));
	}
	
	//list
	public List<Request> listAll(){
		List<Request> requests = requestRepository.findAll();
		return requests;
	}
	
	public PageModel<Request> listAllOnLazyModel(PageRequestModel pr){
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<Request> page = requestRepository.findAll(pageable);
		PageModel<Request> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		return pm;
	}
	
	
	//list by owner
	public List<Request> listAllByOwnerId(Long id){
		List<Request> requests = requestRepository.findAllByOwnerId(id);
		return requests;
	}
	
	public PageModel<Request> listAllByOwnerIdOnLazyModel(Long ownerId, PageRequestModel pr){
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);
		
		PageModel<Request> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pm;
	}
	
}
