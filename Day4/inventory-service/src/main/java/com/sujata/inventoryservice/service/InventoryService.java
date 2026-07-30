package com.sujata.inventoryservice.service;

import java.util.List;

import com.sujata.inventoryservice.entity.Inventory;

public interface InventoryService {

	public Inventory createInventory(Inventory inventory);
	public List<Inventory> getAllInventory();
	public Inventory getInventoryByProductId(Long productId);
	public Inventory updateInventory(Long productId, Inventory inventory);
	public void deleteInventory(Long productId);
	public void reduceStock(Long productId, Integer orderedQuantity);
}
