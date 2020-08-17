package com.example.demo.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.example.demo.models.dao.ProductoDAO;
import com.example.demo.models.document.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoDAO productoDAO;
	
	@GetMapping({"listar","/"})
	public String listar(Model model) {
		
		Flux<Producto> productos = productoDAO.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto; 
				});
		
		productos.subscribe(producto -> log.info(producto.getNombre()));
		
		model.addAttribute("titulo", "Lista de productos");
		model.addAttribute("productos", productos);
		
		return "listar";
	}
	
	@GetMapping("listar-datadriver")
	public String listarDataDriver(Model model) {
		
		Flux<Producto> productos = productoDAO.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto; 
				})
				.delayElements(Duration.ofSeconds(1));
		
		productos.subscribe(producto -> log.info(producto.getNombre()));
		
		// Indicar cantidad de data por segundo indicado en delay
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2));
		model.addAttribute("titulo", "Lista de productos");
		
		return "listar";
	}

	@GetMapping("listar-full")
	public String listarFull(Model model) {
		
		Flux<Producto> productos = productoDAO.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto; 
				})
				.repeat(2000);
		
		productos.subscribe(producto -> log.info(producto.getNombre()));
		
		model.addAttribute("titulo", "Lista de productos");
		model.addAttribute("productos", productos);
		
		return "listar";
	}

	@GetMapping("listar-chunked")
	public String listarChunked(Model model) {
		
		Flux<Producto> productos = productoDAO.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto; 
				})
				.repeat(2000);
		
		productos.subscribe(producto -> log.info(producto.getNombre()));
		
		model.addAttribute("titulo", "Lista de productos");
		model.addAttribute("productos", productos);
		
		return "listar-chunked";
	}
}
