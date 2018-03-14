package com.fileupload.utility;

public class ProcessingException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProcessingException(final String message) {
		super(message);
	}

	public ProcessingException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
