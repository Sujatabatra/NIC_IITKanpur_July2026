package com.sujata.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sujata.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Checks whether a product with the given product code already exists.
     */
    boolean existsByProductCode(String productCode);

    /**
     * Finds a product by its unique product code.
     */
    Optional<Product> findByProductCode(String productCode);

    /**
     * Returns all products belonging to the specified category.
     */
    List<Product> findByCategoryIgnoreCase(String category);

    /**
     * Searches products whose name contains the given keyword.
     */
    List<Product> findByNameContainingIgnoreCase(String keyword);

}