package com.ryutaro.product2.services;


import com.ryutaro.product2.entities.Product;
import com.ryutaro.product2.entities.Productline;
import com.ryutaro.product2.repositories.ProductRepository;
import com.ryutaro.product2.repositories.ProductlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductlineRepository productlineRepository ;

    //TODO: Read Product
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product getProductById(String productCode){
        return productRepository.findProductsByProductCode(productCode);
    }
    public List<Product> findProductsByPriceOrSearchCriteria(String anyContent, BigDecimal lower, BigDecimal upper){
        if (lower.compareTo(upper) > 0) {
            BigDecimal tmp = lower;
            lower = upper;
            upper = tmp;
        }
        return productRepository.findProductsByBuyPriceBetweenOrProductNameContainingOrProductCodeContainingOrProductDescriptionContainingOrderByBuyPrice(lower, upper, anyContent, anyContent, anyContent);
    }
    //TODO: ADD Product
    public Product addProduct(Product product, String productLine) {
        Productline selectedLine = productlineRepository.findById(productLine).orElse(null);
        if (selectedLine == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Product Line not found: ", productLine));

        }
        product.setProductLine(selectedLine); // ถ้่่ามี line ให้ set line
        return productRepository.save(product); // update product
    }

    //TODO : Update Product
    public Product updateProduct(Product product){
        if(product.getProductCode() == null || product.getProductCode().isEmpty() || !productRepository.existsById(product.getProductCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't update, Product id %s does not exists", product.getProductCode()));
        }
        return productRepository.save(product);
    }

    //TODO: Delete Product
    public Product deleteProduct(String productCode){
        Product product = getProductById(productCode);
        if( product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't delete, Product id %s does not exists", productCode));
        }
        productRepository.deleteById(productCode);
        return product;
    }

}

