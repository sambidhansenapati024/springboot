package com.alfaris.ipsh.liquidity.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.exception.RecordUpdateException;



@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private final MessageSource messageSource;

	@Autowired
	public CustomExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.OK);
	}

	@ExceptionHandler(RecordCreateException.class)
	public final ResponseEntity<Object> handleRecordCreationException(RecordCreateException ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.OK);
	}

	@ExceptionHandler(RecordUpdateException.class)
	public final ResponseEntity<Object> handleRecordUpdateException(RecordUpdateException ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse(ex.getMessage(),
				messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), details);
		return new ResponseEntity(error, HttpStatus.OK);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult result = ex.getBindingResult();
		List<ObjectError> allErrors = result.getAllErrors();
		List<JSONObject> errorList = new ArrayList<>();
		for (ObjectError objectError : allErrors) {
			JSONObject obj = new JSONObject();
			FieldError fieldError = (FieldError) objectError;
			obj.put(fieldError.getField(), messageSource.getMessage(objectError, request.getLocale()));
			errorList.add(obj);
		}
		/*
		 * List<String> errorMessages = result.getAllErrors().stream() .map(objectError
		 * -> messageSource.getMessage(objectError, request.getLocale()))
		 * .collect(Collectors.toList());
		 */
		ServiceResponse error = new ServiceResponse("VALERRCOD", "Validation Failed", errorList);
		return new ResponseEntity<>(error, HttpStatus.OK);
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		List<JSONObject> details = new ArrayList<>();
		ServiceResponse error = new ServiceResponse("99999",
				"Permission denied", details);
		return new ResponseEntity(error, HttpStatus.FORBIDDEN);
	}
}
