package com.sujata.inventoryservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sujata.inventoryservice.client.Product;
import com.sujata.inventoryservice.client.ProductClient;
import com.sujata.inventoryservice.entity.Inventory;
import com.sujata.inventoryservice.exception.InsufficientStockException;
import com.sujata.inventoryservice.exception.ResourceNotFoundException;
import com.sujata.inventoryservice.repository.InventoryRepository;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{

	private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

	private final InventoryRepository inventoryRepository;
	private final ProductClient productClient;
	
	public InventoryServiceImpl(InventoryRepository inventoryRepository,ProductClient productClient) {
		this.inventoryRepository = inventoryRepository;
		this.productClient = productClient;
	}

	/**
	 * Create Inventory
	 */
	public Inventory createInventory(Inventory inventory) {

		logger.info("Creating inventory for Product Id : {}", inventory.getProductId());

		Product product=productClient.getProduct(inventory.getProductId());
		if(product==null)
			if (product == null) {
				throw new RuntimeException("Product not found.");
			}

		inventory.setProductName(product.getName());
		
		Inventory savedInventory = inventoryRepository.save(inventory);

		logger.info("Inventory created successfully.");

		return savedInventory;
	}

	/**
	 * Get All Inventory
	 */
	public List<Inventory> getAllInventory() {

		logger.info("Fetching all inventory.");

		return inventoryRepository.findAll();
	}

	/**
	 * Get Inventory By Product Id
	 */
	public Inventory getInventoryByProductId(Long productId) {

		logger.info("Searching inventory for Product Id : {}", productId);

		return inventoryRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found for Product Id : " + productId));
	}

	/**
	 * Update Inventory
	 */
	public Inventory updateInventory(Long productId, Inventory inventory) {

		Inventory existing = getInventoryByProductId(productId);

		existing.setProductName(inventory.getProductName());
		existing.setAvailableQuantity(inventory.getAvailableQuantity());

		logger.info("Inventory updated for Product Id : {}", productId);

		return inventoryRepository.save(existing);
	}

	/**
	 * Delete Inventory
	 */
	public void deleteInventory(Long productId) {

		Inventory inventory = getInventoryByProductId(productId);

		inventoryRepository.delete(inventory);

		logger.info("Inventory deleted for Product Id : {}", productId);

	}

	/**
	 * Reduce Stock
	 */
	public void reduceStock(Long productId, Integer orderedQuantity) {

		Inventory inventory = getInventoryByProductId(productId);

		logger.info("Current Stock : {}", inventory.getAvailableQuantity());

		if (inventory.getAvailableQuantity() < orderedQuantity) {

			logger.error("Insufficient stock for Product Id : {}", productId);

			throw new InsufficientStockException(
			        "Only "
			        + inventory.getAvailableQuantity()
			        + " items available.");
		}

		inventory.setAvailableQuantity(inventory.getAvailableQuantity() - orderedQuantity);

		inventoryRepository.save(inventory);

		logger.info("Inventory Updated. Remaining Quantity : {}", inventory.getAvailableQuantity());

	}

}