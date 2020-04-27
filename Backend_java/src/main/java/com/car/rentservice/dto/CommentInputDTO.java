package com.car.rentservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentInputDTO {

	private final String post;

	@JsonCreator
	public CommentInputDTO(@JsonProperty("post") String post) {
		this.post = post;
	}
}
