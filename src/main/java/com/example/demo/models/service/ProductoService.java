package com.example.demo.models.service;

import com.example.demo.models.document.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

	public Flux<Producto> findAll();
	
	public Flux<Producto> findAllWithUpperCaseName();
	
	public Flux<Producto> findAllWithRepeat();
	
	public Mono<Producto> findById(String id);
	
	public Mono<Producto> save(Producto producto);
	
	public Mono<Void> delete(Producto producto);
}
