package com.ryutaro.product2.controllers;

import com.ryutaro.product2.entities.Product;
import com.ryutaro.product2.services.ProductLineService;
import com.ryutaro.product2.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductLineService productLineService;

    private List<String> getProductLines() {
        return productLineService.getAllProductLineId();
    }
    //TODO: Read
    @GetMapping("")
    public String getAllProduct(Model model){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("page", "products");
        return "product_list";
    }

    @GetMapping("/product_details")
    public String getProductById(@RequestParam String productCode, Model model) {
        Product product = productService.getProductById(productCode);
        model.addAttribute("product", product);
        return "product_details";
    }
    @GetMapping("/searchByAnyContentOrPrice")
    public String search(@RequestParam(required = false) String searchParam,
                         @RequestParam(defaultValue = "10.0") Double lower,
                         @RequestParam(defaultValue = "9999.99") Double upper, Model model) {
        model.addAttribute("products", productService.findProductsByPriceOrSearchCriteria(searchParam,
                BigDecimal.valueOf(lower),
                BigDecimal.valueOf(upper)));
        model.addAttribute("searchParam" , searchParam);
        model.addAttribute( "lower", lower);
        model.addAttribute( "upper", upper);

        return "product_list";
    }

    //TODO: C -> Create
    @GetMapping("/add")
    public String addProduct(Model model){
        model.addAttribute("productLines" , getProductLines());
        return "product_add";
    }
    @PostMapping("/add")
    public void addProduct(Product product, String productLine, HttpServletResponse response) throws IOException {
        productService.addProduct(product, productLine);
        response.sendRedirect("/products");
    }

    //TODO: U -> Update
    @GetMapping("/update")
    public String updateProduct(@RequestParam String productCode, Model model) {
        Product product = productService.getProductById(productCode);
        model.addAttribute("product", product);
        model.addAttribute("productLines", getProductLines());
        model.addAttribute("selectedProductLine", product.getProductLine() != null ? product.getProductLine().getProductLine() : null);
        return "product_update";
    }

    @PostMapping("/update")
    public void updateProduct(Product product , HttpServletResponse response) throws IOException{
        productService.updateProduct(product);
        response.sendRedirect("/products");
    }
    //TODO: D -> Delete
    @GetMapping("/delete")
    public String deleteProductById(@RequestParam String productCode, Model model){
        Product product = productService.deleteProduct(productCode);
        model.addAttribute("product", product);
        model.addAttribute("message", "Product deleted successfully");
        return "product_details";
    }



}
