package com.mrmachine.apifinalapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrmachine.apifinalapp.models.store.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
}
