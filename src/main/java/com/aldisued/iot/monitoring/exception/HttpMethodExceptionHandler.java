package com.aldisued.iot.monitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpMethodExceptionHandler {
	
	@ExceptionHandler(SensorNameInvalidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String sensorNameInvalid(SensorNameInvalidException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(SensorNameNotUniqueException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String sensorNameInvalid(SensorNameNotUniqueException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(AlertNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String alertNotFound(AlertNotFoundException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(SensorNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String sensorNotFOund(SensorNotFoundException e) {
		return e.getMessage();
	}
}
