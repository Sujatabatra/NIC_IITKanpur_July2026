package com.sujata.productservice.service;

import java.util.List;

import com.sujata.productservice.entity.Product;

public interface ProductService {

    /**
     * Creates a new product.
     *
     * @param product Product to be created
     * @return Saved Product
     */
    Product createProduct(Product product);

    /**
     * Updates an existing product.
     *
     * @param id Product Id
     * @param product Updated Product details
     * @return Updated Product
     */
    Product updateProduct(Long id, Product product);

    /**
     * Deletes a product by Id.
     *
     * @param id Product Id
     */
    void deleteProduct(Long id);

    /**
     * Returns a product by Id.
     *
     * @param id Product Id
     * @return Product
     */
    Product getProductById(Long id);

    /**
     * Returns all products.
     *
     * @return List of Products
     */
    List<Product> getAllProducts();

    /**
     * Returns all products belonging to a category.
     *
     * @param category Category Name
     * @return List of Products
     */
    List<Product> searchByCategory(String category);

    /**
     * Searches products by name.
     *
     * @param keyword Product Name
     * @return List of Products
     */
    List<Product> searchByName(String keyword);

}