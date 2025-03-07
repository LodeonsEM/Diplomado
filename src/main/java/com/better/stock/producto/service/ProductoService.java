package com.better.stock.producto.service;

import java.util.List;
import java.util.Optional;

import com.better.stock.producto.domain.Producto;


public interface ProductoService {

    List<Producto> findAllProductos();

    Optional<Producto> findProductoByCodigo(String codigo);

    Producto create(Producto producto);
    
    Producto actualizarProductoDescrip(String codigo, String descripcion);
    
    Producto actualizarProductoCantidad(String codigo, int Cantidad );
    
    Producto actualizarProductoCaract(String codigo, String Caracteristicas );
    
    Producto actualizarProductoPrecioCat(String codigo, double precioCatalogo );

    void deleteProducto(String id);
}