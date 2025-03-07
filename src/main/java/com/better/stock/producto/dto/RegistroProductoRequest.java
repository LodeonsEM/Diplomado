package com.better.stock.producto.dto;

import com.better.stock.producto.domain.Producto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RegistroProductoRequest {
	
	@NotNull
	@Valid
	private Producto producto;
}
