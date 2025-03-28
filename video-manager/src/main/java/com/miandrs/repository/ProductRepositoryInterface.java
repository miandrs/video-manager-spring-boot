package com.miandrs.repository;

import java.util.Optional;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.miandrs.models.entity.Product;

public interface ProductRepositoryInterface extends CrudRepository<Product, UUID> {
	@EntityGraph(value = "product.category", type = EntityGraph.EntityGraphType.FETCH)
	Iterable<Product> findAll();
	@EntityGraph(value = "product.category", type = EntityGraph.EntityGraphType.FETCH)
	Optional<Product> findById(UUID id);
}
