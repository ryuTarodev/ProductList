package com.ryutaro.product2.services;

import com.ryutaro.product2.entities.Productline;
import com.ryutaro.product2.repositories.ProductlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductLineService {
    @Autowired
    private ProductlineRepository productlineRepository;

    public List<String> getAllProductLineId() {return productlineRepository.findAllProductLineId();};



}
