package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dao.ProductoDAO;
import com.example.demo.models.document.Producto;
import com.example.demo.models.service.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping
	public Flux<Producto> index() {
		Flux<Producto> flujoProductos = productoService.findAll();
		return flujoProductos;
	}

	@GetMapping("/{id}")
	public Mono<Producto> show(@PathVariable String id) {
//		1era forma, mas sencilla
//		Mono<Producto> producto = productoDAO.findById(id);
//		return producto;
		
//		2da forma, filtrando
		Flux<Producto> flujoProductos = productoService.findAll();
		Mono<Producto> producto = flujoProductos.filter(singleProduct -> singleProduct.getId().equals(id))
				.next(); //Next retorna solo un mono
		
		return producto;
	}
	
}
