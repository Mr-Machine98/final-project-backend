package com.mrmachine.apifinalapp.models.store;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.ToString;

@Entity
@Table(name = "especificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Specification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String peso;
	private String motor;
	
	@Column(name = "potencia_motor")
	private String potenciaMotor;
	
	@Column(name = "torque_motor")
	private String torqueMotor;
	
	@JsonIgnore
	@JoinColumn(name = "product_id")
	@OneToOne(fetch = FetchType.LAZY)
	private Product product;
}
