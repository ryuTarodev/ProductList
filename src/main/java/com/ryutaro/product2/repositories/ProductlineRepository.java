package com.ryutaro.product2.repositories;

import com.ryutaro.product2.entities.Productline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductlineRepository extends JpaRepository<Productline, String> {

    @Query("SELECT p.productLine FROM Productline p")
    List<String> findAllProductLineId();
}