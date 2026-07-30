package com.sujata.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sujata.inventoryservice.entity.Inventory;
import com.sujata.inventoryservice.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventory")
@Validated
public class InventoryController {

	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	/**
	 * Create Inventory
	 */
	@PostMapping
	public ResponseEntity<Inventory> createInventory(@Valid @RequestBody Inventory inventory) {

		Inventory savedInventory = inventoryService.createInventory(inventory);

		return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
	}

	/**
	 * Get All Inventory
	 */
	@GetMapping
	public ResponseEntity<List<Inventory>> getAllInventory() {

		return ResponseEntity.ok(inventoryService.getAllInventory());

	}

	/**
	 * Get Inventory By Product Id
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {

		return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));

	}

	/**
	 * Update Inventory
	 */
	@PutMapping("/{productId}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable Long productId,
			@Valid @RequestBody Inventory inventory) {

		return ResponseEntity.ok(inventoryService.updateInventory(productId, inventory));

	}

	/**
	 * Delete Inventory
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteInventory(@PathVariable Long productId) {

		inventoryService.deleteInventory(productId);

		return ResponseEntity.noContent().build();

	}

}