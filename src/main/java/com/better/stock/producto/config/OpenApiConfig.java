package com.better.stock.producto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
			version = "v1",
			title = "Betterware Producto Microservice Endpoints", 
			description = "Definición de los Endpoints de "
					+ "Producto Service para el sistema Betterware", 
			contact = @Contact(
				name = "Enrique Mares Mendoza", 
				url = "https://www.unam.mx", 
			email = "enrique.mares@unam.mx")))
public class OpenApiConfig {

}
