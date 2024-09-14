package com.wish.dms_api.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wish.dms_api.response.ResponseHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		List<ObjectError> allErrors= ex.getBindingResult().getAllErrors();
		Map<String, String> error= new LinkedHashMap<>();
		allErrors.forEach(err->{
			String message= err.getDefaultMessage();
			String field = (err instanceof FieldError) ? ((FieldError) err).getField() : err.getObjectName();
			error.put(field, message);
		});
		return ResponseHandler.response(
				ResponseHandler.FAILURE_CODE,
				error,
				
				ResponseHandler.FAILURE_MESSAGE+" : "+ex.getMessage()
				);
//		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
