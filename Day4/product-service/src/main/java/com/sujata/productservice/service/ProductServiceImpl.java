package com.sujata.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sujata.productservice.entity.Product;
import com.sujata.productservice.exception.DuplicateProductException;
import com.sujata.productservice.exception.ProductNotFoundException;
import com.sujata.productservice.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product createProduct(Product product) {

        if (repository.existsByProductCode(product.getProductCode())) {
            throw new DuplicateProductException(
                    "Product Code '" + product.getProductCode() + "' already exists.");
        }

        return repository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with Id : " + id));

        existingProduct.setProductCode(product.getProductCode());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        

        return repository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with Id : " + id));

        repository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with Id : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {

        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchByCategory(String category) {

        return repository.findByCategoryIgnoreCase(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchByName(String keyword) {

        return repository.findByNameContainingIgnoreCase(keyword);
    }

}