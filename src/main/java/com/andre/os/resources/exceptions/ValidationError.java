package com.andre.os.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessade> errors = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(Long timestamp, Integer status, String error) {
		super(timestamp, status, error);
	}

	public List<FieldMessade> getErros() {
		return errors;
	}

	public void addErros(String fieldName, String message) {
		this.errors.add(new FieldMessade(fieldName, message));
	}
}
