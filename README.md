# Entities
### Product
- from classicModels DB
### Productline
- from classicModels DB

# Repositories
### ProductlineRepo
- List<String> findAllProductLineId()
  - query all productline FROM Productline
  ```
  @Query("SELECT p.productLine FROM Productline p")
  ```
### ProductRepo
```
List<Product> findProductsByBuyPriceBetweenOrProductNameContainingOrProductCodeContainingOrProductDescriptionContainingOrderByBuyPrice(
              BigDecimal lower, BigDecimal upper,String productName
              , String productCode, String productDescription
);
  
```
  - for filter price by anycontent and buyPrice but we already use jquery thing
            
# Services
### ProductlineService
- Injected productlineRepo
  ```
  @Autowired
      private ProductlineRepository productlineRepository;
  ```
- getAllproductline
  ```
  public List<String> getAllProductLineId() {return productlineRepository.findAllProductLineId();};
  ```
### ProductService (Based on CRUD)
- Injected productRepo
- Injected productlineRepo

- Create
```
    public Product addProduct(Product product, String productLine) {
        Productline selectedLine = productlineRepository.findById(productLine).orElse(null);
        if (selectedLine == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Product Line not found: ", productLine));

        }
        product.setProductLine(selectedLine); 
        return productRepository.save(product); 
    }
```
- Read
```
public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product getProductById(String productCode){

        return productRepository.findById(productCode).orElse(null);
    }
    public List<Product> findProductsByPriceOrSearchCriteria(String anyContent, BigDecimal lower, BigDecimal upper){
        if (lower.compareTo(upper) > 0) {
            BigDecimal tmp = lower;
            lower = upper;
            upper = tmp;
        }
        return productRepository.findProductsByBuyPriceBetweenOrProductNameContainingOrProductCodeContainingOrProductDescriptionContainingOrderByBuyPrice(lower, upper, anyContent, anyContent, anyContent);
    }
```
- Update
```
public Product updateProduct(Product product){
        if(product.getProductCode() == null || product.getProductCode().isEmpty() || !productRepository.existsById(product.getProductCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't update, Product id %s does not exists", product.getProductCode()));
        }
        return productRepository.save(product);
    }
```
- Delete
```
public Product deleteProduct(String productCode){
        Product product = getProductById(productCode);
        if( product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Can't delete, Product id %s does not exists", productCode));
        }
        productRepository.deleteById(productCode);
        return product;
    }
```

# Controllers (CRUD)
## ProductController
- Injected productService
- Injected productlineService
- Read
  - @GetMapping getAllProduct()
  - @GetMapping getProductById()
  - @GetMapping search()
- Create
  - @GetMapping addProduct()
  - @PostMapping addProduct()
- Update
  - @GetMapping updateProduct()
  - @PostMapping updateProduct()   
- Delete
  - @GetMapping deleteProductById

# Templates
## flagments
### html-head.html
- import jquery tailwindcss
## product_add.html
- form for add product
## product_details.html
- show product details
## product_list.html
- show product_list
## proudct update.html
- form for update product
