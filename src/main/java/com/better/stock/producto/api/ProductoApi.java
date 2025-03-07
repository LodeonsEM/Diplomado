package com.better.stock.producto.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.better.stock.producto.domain.Producto;
import com.better.stock.producto.dto.RegistroProductoRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RequestMapping(path="api/producto", produces="application/json")
@CrossOrigin(origins="*")
@Tag(name="producto", description="API del Recurso Producto")
public interface ProductoApi {
	
	@Operation(summary = "Registrar Producto")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "201", 
		description = "Producto Creado Exitosamente", 
	    content = { 
	    	@Content(mediaType="application/json", 
	    	schema = @Schema(implementation=Producto.class)) }),
	  @ApiResponse(responseCode = "409", 
	  	description="Ya existe un producto con el codigo especificado", 
	    content = @Content) })
	@PostMapping(path="registro", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	Producto registrarProducto(
		@NotNull @Valid @RequestBody RegistroProductoRequest request);
}

