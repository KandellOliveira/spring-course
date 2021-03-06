package com.example.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.springcourse.domain.Request;
import com.example.springcourse.domain.RequestStage;
import com.example.springcourse.domain.User;
import com.example.springcourse.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RequestUpdatedto {
	
	@NotBlank(message = "Subject required")
	private String subject;
	private String description;
	
	@NotNull(message = "State required")
	private RequestState requestState;
	
	@NotNull(message = "Owner required")
	private User owner;
	private List<RequestStage> stages = new ArrayList<RequestStage>();
	
	public Request transformToRequest() {
		Request request = new Request(null, this.subject, this.description, null, this.requestState, this.owner, this.stages);
		return request;
	}

}
