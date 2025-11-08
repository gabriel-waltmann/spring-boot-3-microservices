package com.gabriel.microservices.product.repository;

import com.gabriel.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}