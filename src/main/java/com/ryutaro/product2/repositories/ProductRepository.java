package com.ryutaro.product2.repositories;

import com.ryutaro.product2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductsByProductCode(String productCode);

    List<Product> findProductsByBuyPriceBetweenOrProductNameContainingOrProductCodeContainingOrProductDescriptionContainingOrderByBuyPrice(
            BigDecimal lower, BigDecimal upper,
            String productName, String productCode, String productDescription
    );
}