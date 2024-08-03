package com.mrmachine.apifinalapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrmachine.apifinalapp.models.store.ItemStore;
import com.mrmachine.apifinalapp.models.store.Sale;
import com.mrmachine.apifinalapp.services.ItemStoreService;

@RestController
@RequestMapping("/api/final-app")
public class ItemStoreController {
	
	private ItemStoreService itemStoreService;
	
	public ItemStoreController(ItemStoreService itemStoreService) {
		this.itemStoreService = itemStoreService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<ItemStore>> getAllItems() {
		return ResponseEntity.ok(itemStoreService.getAllItems());
	}
	
	@PostMapping("/addsales")
	public ResponseEntity<?> addSales(@RequestBody List<Sale> sales) {
		Optional<?> res = this.itemStoreService.addSales(sales);
		if (res.isPresent()) {			
			return ResponseEntity.ok(res.get());
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
