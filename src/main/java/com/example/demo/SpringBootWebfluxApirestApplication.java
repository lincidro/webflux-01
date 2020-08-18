package com.example.demo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.example.demo.models.dao.ProductoDAO;
import com.example.demo.models.document.Producto;
import com.example.demo.models.service.ProductoService;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApirestApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApirestApplication.class);
	
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApirestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		
		Flux.just(
				new Producto("Marca 1", 120.00),
				new Producto("Marca 2", 220.00),
				new Producto("Marca 3", 320.00),
				new Producto("Marca 4", 420.00),
				new Producto("Marca 5", 520.00),
				new Producto("Marca 6", 620.00)
				)
		.flatMap(producto -> {
			producto.setCreatedAt(new Date());
			return productoService.save(producto);
		})
		.subscribe(producto -> log.info("Mongo insert :" + producto.getId() + producto.getNombre()));
	}

}
