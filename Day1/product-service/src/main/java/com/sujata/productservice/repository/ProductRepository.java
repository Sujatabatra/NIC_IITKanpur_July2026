package com.sujata.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sujata.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
