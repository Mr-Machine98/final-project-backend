package com.mrmachine.apifinalapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrmachine.apifinalapp.models.store.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
