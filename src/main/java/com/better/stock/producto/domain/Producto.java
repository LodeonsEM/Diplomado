package com.better.stock.producto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Document(collection = "productos")
public class Producto {

	@Id
    private String id;
    
    @NotBlank(message="Codigo no puede ser blanco")
    private String codigo; // Código completo
    
    private String codigoCorto = ""; // Código corto (generado)
    
    private String descripcion;
    
    private int cantidad;
    
    //@NotBlank(message="Precio Catalogo no puede ser blanco")
    private double precioCatalogo;
    
    private double precioAsociado = 0; // Precio Asociado (generado)
    
    private String caracteristicas;
    


}