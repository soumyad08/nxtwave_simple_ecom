package com.example.simple_ecommerce.service;

import com.example.simple_ecommerce.Entity.Product;
import com.example.simple_ecommerce.dto.ProductRequest;
import com.example.simple_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Product Creation
    public Product createProduct(ProductRequest request){
        Optional<Product> isExist = productRepository.findByName(request.getName());
        if(isExist.isPresent()){
            throw new RuntimeException("Product Already Exists.");
        }

        Product product = Product.builder()
                                .name(request.getName())
                                .description(request.getDescription())
                                .price(request.getPrice())
                                .build();

        return productRepository.save(product);
    }


    // Fetch Product
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    // Fetch Product based on ID
    public Product getProductById(String id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id" + id));
    }

    // Update of My Product
    public Product updateProduct(String id, ProductRequest request){
        Product isExistingProd = productRepository.findById(id).orElseThrow(()->
                  new RuntimeException("Product not found with id " + id));

        isExistingProd.setName(request.getName());
        isExistingProd.setDescription(request.getDescription());
        isExistingProd.setPrice(request.getPrice());

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
