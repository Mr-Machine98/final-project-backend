package com.mrmachine.apifinalapp.models.store;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "log_ventas")
public class LogSale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Integer quantity;
	
	private BigDecimal subTotal;
	
	private String dateSale;
	
	private Long saleId;
	
	private String product;
	
	private String owner;
}
