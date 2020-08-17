package com.example.demo.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.models.document.Producto;

public interface ProductoDAO extends ReactiveMongoRepository<Producto, String>{

}
