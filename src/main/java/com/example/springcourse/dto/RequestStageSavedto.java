package com.example.springcourse.dto;

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
public class RequestStageSavedto {
	
	private String descrition;
	@NotNull(message = "State required")
	private RequestState requestState;
	
	@NotNull(message = "Request required")
	private Request request;
	
	@NotNull(message = "Owner required")
	private User owner;
	
	public RequestStage transformToRequestStage() {
		RequestStage requestStage = new RequestStage(null, this.descrition, null, this.requestState, this.request, this.owner);
		return requestStage;
	}

}
