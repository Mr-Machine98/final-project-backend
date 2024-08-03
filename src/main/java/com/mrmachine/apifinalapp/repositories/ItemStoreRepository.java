package com.mrmachine.apifinalapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mrmachine.apifinalapp.models.store.ItemStore;

@Repository
public interface ItemStoreRepository extends JpaRepository<ItemStore, Long> {

	@Query("select its from ItemStore its where its.product.id = :productId")
	public ItemStore findByProductId(@Param("productId") Long id);
}
