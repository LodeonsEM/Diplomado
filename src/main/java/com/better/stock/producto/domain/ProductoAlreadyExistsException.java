package com.better.stock.producto.domain;

public class ProductoAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = -4575106698172398574L;

	public ProductoAlreadyExistsException(String codigo) {
		super("Ya existe un producto registrado con el codigo: " + codigo);
	}
}
