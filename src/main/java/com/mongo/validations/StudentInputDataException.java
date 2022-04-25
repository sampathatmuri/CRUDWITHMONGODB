package com.mongo.validations;

import java.util.List;

public class StudentInputDataException extends RuntimeException {

	private static final long serialVersionUID = 6182095150529534893L;

	private List<String> messages;

	public StudentInputDataException() {
		super();
	}

	public StudentInputDataException(String message) {
		super(message);
	}

	public StudentInputDataException(List<String> messages) {
		super();
		this.messages = messages;
	}

	public StudentInputDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public StudentInputDataException(Throwable cause) {
		super(cause);
	}

	public List<String> getMessages() {
		return this.messages;
	}

}
