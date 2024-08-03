package com.mrmachine.apifinalapp.services;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mrmachine.apifinalapp.models.store.ItemStore;
import com.mrmachine.apifinalapp.models.store.Product;
import com.mrmachine.apifinalapp.models.store.Sale;
import com.mrmachine.apifinalapp.repositories.ItemStoreRepository;
import com.mrmachine.apifinalapp.repositories.ProductRepository;
import com.mrmachine.apifinalapp.repositories.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemStoreService {

	private ItemStoreRepository itemStoreRepo;
	private SaleRepository saleRepo;
	private ProductRepository productRepo;
	private JdbcTemplate jdbcTemplate;

	public ItemStoreService(ItemStoreRepository itemStoreRepo, SaleRepository saleRepo, ProductRepository productRepo,
			JdbcTemplate jdbcTemplate) {
		this.itemStoreRepo = itemStoreRepo;
		this.saleRepo = saleRepo;
		this.productRepo = productRepo;
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ItemStore> getAllItems() {
		return itemStoreRepo.findAll();
	}

	@Transactional
	public Optional<?> addSales(List<Sale> sales) {

		List<Sale> salesTosave = new ArrayList<>();

		try {
			for (Sale sale : sales) {

				Sale s = new Sale();
				s.setSubTotal(sale.getSubTotal());
				s.setQuantity(sale.getQuantity());
				s.setDateSale(LocalDateTime.now());
				s.setOwner(sale.getOwner());

				Product p = productRepo.findById(sale.getProduct().getId()).get();
				s.setProduct(p);

				salesTosave.add(s);
			}

			List<Sale> currentSales = this.saleRepo.saveAllAndFlush(salesTosave);

			for (Sale sale : currentSales) {
				ItemStore item = itemStoreRepo.findByProductId(sale.getProduct().getId());
				item.setQuantity(item.getQuantity() - sale.getQuantity());
				this.itemStoreRepo.save(item);
			}

			String[] bitacora = currentSales.stream()
					.map(i -> String.format("('%s','%s','%s','%s','%s','%s')",
							i.getQuantity().toString(),
							i.getProduct().getName(),
							String.valueOf(i.getId()),
							i.getSubTotal().toString(),
							i.getDateSale().toString(),
							i.getOwner().toString()

					)).toArray(String[]::new);

			// Use try-with-resources to manage the connection and callable statement
            try (Connection connection = this.jdbcTemplate.getDataSource().getConnection()) {
                Array bitaToSend = connection.createArrayOf("log_sale", bitacora);
                try (CallableStatement cs = connection.prepareCall("CALL my_procedure(?)")) {
                    cs.setArray(1, bitaToSend);
                    cs.execute();
                }
            }

			if (!currentSales.isEmpty()) {
				return Optional.of("La compra se realizó correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return Optional.empty();
	}

}
