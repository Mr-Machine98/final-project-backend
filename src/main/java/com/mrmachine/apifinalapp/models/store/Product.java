package com.mrmachine.apifinalapp.models.store;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String model;
	private BigDecimal price;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(name = "url_img", columnDefinition = "TEXT")
	private String urlImg;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Specification specification;
	
	@JsonIgnore
	@OneToOne(mappedBy = "product")
	private ItemStore itemStore;
	
	@JsonIgnore
    @OneToMany(mappedBy = "product", orphanRemoval = true)
	private List<Sale> sell;
	
}
	