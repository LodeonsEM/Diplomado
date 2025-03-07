package com.better.stock.producto.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.better.stock.producto.domain.Producto;


@Repository
public interface ProductoRepository 
	extends MongoRepository<Producto, String>{
	
	Optional<Producto> findByCodigo(String Codigo);

}