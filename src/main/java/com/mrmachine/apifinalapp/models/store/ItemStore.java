package com.mrmachine.apifinalapp.models.store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items_store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemStore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String lote;
	private String category;
	
	private Integer quantity;
	
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
