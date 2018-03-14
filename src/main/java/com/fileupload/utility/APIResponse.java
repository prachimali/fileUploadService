package com.fileupload.utility;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class APIResponse {

	private Object objectDetails;

	private List<String> errors;

	public APIResponse(final Object objectDetails, final List<String> errors) {
		this.objectDetails = objectDetails;
		this.errors = errors;
	}
}
