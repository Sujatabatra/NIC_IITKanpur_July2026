package com.sujata.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sujata.productservice.entity.Product;
import com.sujata.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repository;//=new ProductRepository();;
	
	
	public ProductServiceImpl(ProductRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	public List<Product> getAllProducts() {
		
		return repository.findAll();
	}

}
