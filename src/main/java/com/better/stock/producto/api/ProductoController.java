package com.better.stock.producto.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.better.stock.producto.domain.Producto;
import com.better.stock.producto.dto.RegistroProductoRequest;
import com.better.stock.producto.service.ProductoService;


@RestController
@RequestMapping(path="api/productos", produces="application/json")
@CrossOrigin(origins="*")
public class ProductoController implements ProductoApi{
	
	@Autowired
    private ProductoService productoService;
	
	@Override
	public Producto registrarProducto(RegistroProductoRequest request) {
		// TODO Auto-generated method stub
		return productoService.create(request.getProducto());
	}

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.findAllProductos();
    }
    
    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> obtenerPorCodigo(@PathVariable("codigo") String codigo) {
        Optional<Producto> producto = productoService.findProductoByCodigo(codigo);
    	if (producto.isPresent()) {
    		return new ResponseEntity<>(producto.get(), HttpStatus.OK);
    	}
    	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    /* 
    @PutMapping(path="{codigo}", consumes="application/json")
	public ResponseEntity<Producto> actualizarProducto(
			@PathVariable("codigo") String codigo, Producto producto) {
		Producto productoActualizado = productoService.actualizarProducto(codigo, producto);
		if (productoActualizado != null) {
			return new ResponseEntity<>(productoActualizado, HttpStatus.OK) ;
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}*/
    
    @PutMapping(path="/actualizar/descricion/{codigo}", consumes="application/json")
	public ResponseEntity<Producto> actualizarProductoDescripcion(
			@PathVariable("codigo") String codigo, @RequestBody String descripcion) {
		Producto productoActualizado = productoService.actualizarProductoDescrip(codigo, descripcion);
		if (productoActualizado != null) {
			return new ResponseEntity<>(productoActualizado, HttpStatus.OK) ;
		} 
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
    
    @PutMapping(path="/actualizar/cantidad/{codigo}", consumes="application/json")
	public ResponseEntity<Producto> actualizarProductoCantidad(
			@PathVariable("codigo") String codigo, @RequestBody int cantidad) {
		Producto productoActualizado = productoService.actualizarProductoCantidad(codigo, cantidad);
		if (productoActualizado != null) {
			return new ResponseEntity<>(productoActualizado, HttpStatus.OK) ;
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
    
    @PutMapping(path="/actualizar/caracteristica/{codigo}", consumes="application/json")
	public ResponseEntity<Producto> actualizarProductoCaracteristicas(
			@PathVariable("codigo") String codigo, @RequestBody String caracteristicas) {
		Producto productoActualizado = productoService.actualizarProductoCaract(codigo, caracteristicas);
		if (productoActualizado != null) {
			return new ResponseEntity<>(productoActualizado, HttpStatus.OK) ;
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
    
    @PutMapping(path="/actualizar/precioCatalogo/{codigo}", consumes="application/json")
	public ResponseEntity<Producto> actualizarProductoPrecioCatalogo(
			@PathVariable("codigo") String codigo, @RequestBody double precioCatalogo) {
		Producto productoActualizado = productoService.actualizarProductoPrecioCat(codigo, precioCatalogo);
		if (productoActualizado != null) {
			return new ResponseEntity<>(productoActualizado, HttpStatus.OK) ;
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
    
    
	@DeleteMapping("{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable("codigo") String codigo) {
		productoService.deleteProducto(codigo);
	}


}


