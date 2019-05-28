package com.example.springcourse.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.User;
import com.example.springcourse.dto.UserLogindto;
import com.example.springcourse.model.PageModel;
import com.example.springcourse.model.PageRequestModel;
import com.example.springcourse.service.RequestService;
import com.example.springcourse.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
public class UserResource {
	
	@Autowired private UserService userService;
	@Autowired private RequestService requestService;
	
	//save
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user){
		User createdUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);		
	}
	
	//update
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody User user){
		user.setId(id);
		User updatedUser = userService.update(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	//getById
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") Long id){
		User user = userService.getById(id);
		return ResponseEntity.ok(user);
		
	}
	
	//list
	@GetMapping
	public ResponseEntity<PageModel<User>> listAll(
			@RequestParam(value = "page") int page, 
			@RequestParam(value = "size") int size) {
		
		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<User> pm = userService.listAllOnLazyModel(pr);
		return ResponseEntity.ok(pm);
	} 
	
	/*
	 * //list
	 * 
	 * @GetMapping public ResponseEntity<List<User>> listAll(){ List<User> users =
	 * userService.listAll(); return ResponseEntity.ok(users); }
	 */
	
	
	//login
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody UserLogindto user){
		User loggedUser = userService.login(user.getEmail(), user.getPassword());
		return ResponseEntity.ok(loggedUser);
	}
	
	/*
	 * //lista all by owner id
	 * 
	 * @GetMapping("/{id}/requests") public ResponseEntity<List<Request>>
	 * listAllRequestById(@PathVariable(name = "id") Long id){ List<Request>
	 * requests = requestService.listAllByOwnerId(id); return
	 * ResponseEntity.ok(requests);
	 * 
	 * }
	 */
	//lista all by owner id
	@GetMapping("/{id}/requests")
	public ResponseEntity<PageModel<Request>> listAllRequestById(
			@PathVariable(name = "id") Long id, 
			@RequestParam(value = "size") int size, 
			@RequestParam(value = "page") int page ){
		PageRequestModel pr  = new PageRequestModel(page, size);
		PageModel<Request> pm = requestService.listAllByOwnerIdOnLazyModel(id, pr);
		return ResponseEntity.ok(pm);
		
	}

}
