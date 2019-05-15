package com.example.springcourse.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PageModel<T> {

	private int totalElements;
	private int pageSize;
	private int totalPages;
	private List<T> elements;
}
