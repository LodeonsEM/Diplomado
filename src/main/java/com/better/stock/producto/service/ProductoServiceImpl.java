package com.better.stock.producto.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.better.stock.producto.domain.Producto;
import com.better.stock.producto.domain.ProductoAlreadyExistsException;
import com.better.stock.producto.repository.ProductoRepository;



@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
    private ProductoRepository productoRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductoServiceImpl.class);
	
	@Override
	public List<Producto> findAllProductos(){
		return productoRepository.findAll();
	}
	
	@Override
    public Optional<Producto> findProductoByCodigo(String codigo){
    	return productoRepository.findByCodigo(codigo);
    }

	@Override
	public Producto create(Producto producto) {
		// validacion usuario existente
		Optional<Producto> usuarioExistente = 
				productoRepository.findByCodigo(producto.getCodigo());
		if (usuarioExistente.isPresent()) {
			throw new ProductoAlreadyExistsException(producto.getCodigo());
		}
        producto.setCodigoCorto(generarCodigoCorto(producto.getCodigo()));
        producto.setPrecioAsociado(generarPrecioAsociados(producto.getPrecioCatalogo())); 
		productoRepository.save(producto);
		LOG.info("Usuario Registrado: " + producto);
		
		return producto;
	}


	@Override
    public void deleteProducto(String id) {
    	productoRepository.deleteById(id);
    }

	@Override
	public Producto actualizarProductoDescrip(String codigo, String descripcion) {
		Optional<Producto> productoExistente = productoRepository.findByCodigo(codigo);
		System.out.println(productoExistente);
		if (productoExistente.isPresent()) {
			Producto productoActualizar = productoExistente.get();
			productoActualizar.setDescripcion(descripcion);
			productoRepository.save(productoActualizar);
			return productoActualizar;
		}
		return null;
	}

	@Override
	public Producto actualizarProductoCantidad(String codigo, int cantidad) {
		Optional<Producto> productoExistente = productoRepository.findByCodigo(codigo);
		if (productoExistente.isPresent()) {
			Producto productoActualizar = productoExistente.get();
			productoActualizar.setCantidad(cantidad);
			productoRepository.save(productoActualizar);
			return productoActualizar;
		}
		return null;
	}

	@Override
	public Producto actualizarProductoCaract(String codigo, String caracteristicas) {
		Optional<Producto> productoExistente = productoRepository.findByCodigo(codigo);
		if (productoExistente.isPresent()) {
			Producto productoActualizar = productoExistente.get();
			productoActualizar.setCaracteristicas(caracteristicas);
			productoRepository.save(productoActualizar);
			return productoActualizar;
		}
		return null;
	}

	@Override
	public Producto actualizarProductoPrecioCat(String codigo, double precioCatalogo) {
		Optional<Producto> productoExistente = productoRepository.findByCodigo(codigo);
		if (productoExistente.isPresent()) {
			Producto productoActualizar = productoExistente.get();
			productoActualizar.setPrecioCatalogo(precioCatalogo);
			productoActualizar.setPrecioAsociado(generarPrecioAsociados(precioCatalogo));
			productoRepository.save(productoActualizar);
			return productoActualizar;
		}
		return null;
	}
	
    private String generarCodigoCorto(String codigo) {
        // Retorna el código sin los primeros 2 caracteres que reprecenta el año de venta del producto
        return codigo.length() > 2 ? codigo.substring(2) : codigo;
    }
    
    private double generarPrecioAsociados(double precioCatalogo) {
        // Retorna el precio que les corresponde a los asociados
        return precioCatalogo * 0.8135;
    }
    
    

}

