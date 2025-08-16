package com.example.simple_ecommerce.service;

import com.example.simple_ecommerce.Entity.Category;
import com.example.simple_ecommerce.Entity.Product;
import com.example.simple_ecommerce.Exception.ProductExistsException;
import com.example.simple_ecommerce.dto.ProductRequest;
import com.example.simple_ecommerce.repository.CategoryRepo;
import com.example.simple_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepo categoryRepository;


    public Product saveProduct(Product product) {
        // Fetch category by ID (assuming request sends categoryId)
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository
                    .findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);  // ✅ Set managed entity
        } else {
            throw new RuntimeException("Category is required for product");
        }

        return productRepository.save(product);
    }

    // Product Creation
    public Product createProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());

//        Optional<Product> isExist = productRepository.findByName(request.getName());
//        if(isExist.isPresent()){
//            throw new ProductExistsException("Product Already Exists.");
//        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() ->
                new RuntimeException("Category Not Found with Id: " + request.getCategoryId()));

        product.setCategory(category);
        return productRepository.save(product);
    }

    // Fetch Product
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    // Fetch Product based on ID
    public Product getProductById(String id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductExistsException("Product not found with id" + id));
    }

    // Update of My Product
    public Product updateProduct(String id, ProductRequest request){
        Product isExistingProd = productRepository.findById(id).orElseThrow(()->
                  new RuntimeException("Product not found with id " + id));

        if(request.getName() != null) {
            isExistingProd.setName(request.getName());
        }

        if(request.getDescription() != null) {
            isExistingProd.setDescription(request.getDescription());
        }

        if(request.getPrice() != null) {
            isExistingProd.setPrice(request.getPrice());
        }

        return productRepository.save(isExistingProd);
    }

    // Delete The Product
    public void deleteById(String id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException(" Product not found with id " + id);
        }

        productRepository.deleteById(id);
    }
}
