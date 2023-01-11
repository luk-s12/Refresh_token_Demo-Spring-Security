package com.example.auth.controllers.errors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.auth.exceptions.CustomError;
import com.example.auth.exceptions.EmailAlreadyUsedException;
import com.example.auth.exceptions.EmptyRefreshTokenException;
import com.example.auth.exceptions.InvalidTokenException;
import com.example.auth.exceptions.RoleNotFoundException;
import com.example.auth.exceptions.SignupException;
import com.example.auth.exceptions.UserAuthenticationException;
import com.example.auth.utils.Constant;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public CustomError emptyRefreshToken(EmptyRefreshTokenException ex, WebRequest request) {
		return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, Constant.EMPTY_REFRESH_TOKEN, null);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomError invalidToken(InvalidTokenException ex, WebRequest request) {
		return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, Constant.INVALID_REFRESH_TOKEN, null);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomError expiredJwt(ExpiredJwtException ex, WebRequest request) {
		return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, Constant.EXPIRED_REFRESH_TOKEN, null);
	}

	@ExceptionHandler
	@ResponseStatus
	public CustomError emailAlreadyUsed(EmailAlreadyUsedException ex, WebRequest request) {
		return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, null, null);
	}

	@ExceptionHandler
	@ResponseStatus
	public CustomError usernameNotFound(UsernameNotFoundException ex, WebRequest request) {
		return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, null, null);
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomError roleNotFound(RoleNotFoundException ex, WebRequest request) {
		return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, null, null);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public CustomError userAuthentication(UserAuthenticationException ex, WebRequest request) {
		return buildException(HttpStatus.UNAUTHORIZED, ex, request, null, null);
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public CustomError signup(SignupException ex, WebRequest request) {
		return buildException(HttpStatus.UNAUTHORIZED, ex, request, ex.getMessage(), ex.getErrors());

	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public CustomError globalExceptionHandler(Exception ex, WebRequest request) {
		List<String> errors = new ArrayList<>();
		errors.add("Oops ocurrio algun problema");
		return buildException(HttpStatus.INTERNAL_SERVER_ERROR, ex, request, null, errors);
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public CustomError accessDenied(AccessDeniedException ex, WebRequest request) {
		List<String> errors = new ArrayList<>();
		errors.add("Acceso de no autorizado");
		return buildException(HttpStatus.UNAUTHORIZED, ex, request, Constant.ACCESS_DENIED, errors);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
		CustomError response = buildException(HttpStatus.BAD_REQUEST, ex, request, Constant.FIELD_ERROR, errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	private CustomError buildException(HttpStatus httpStatus, Exception ex, WebRequest request, String message, List<String> errors) {

		if (message == null)
			message = "";

		if (errors == null)
			errors = new ArrayList<>();

		if (errors.isEmpty())
			errors.add(ex.getMessage());

		return new CustomError.BuilderError()
							  .status(httpStatus)
							  .statusCode(httpStatus)
							  .path(request)
							  .message(message)
							  .errors(errors)
							  .build();
	}
}
