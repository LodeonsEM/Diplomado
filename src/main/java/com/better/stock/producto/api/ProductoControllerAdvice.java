package com.better.stock.producto.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.better.stock.producto.domain.ProductoAlreadyExistsException;

@RestControllerAdvice
public class ProductoControllerAdvice {
	
	@ExceptionHandler(ProductoAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	private String usuarioAlreadyExistsHandler(
			ProductoAlreadyExistsException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private String validatorHandler(
			MethodArgumentNotValidException exception) {
		return "Existen errores de validacion en el payload";
	}
}



