package com.example.simple_ecommerce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductExistsException extends RuntimeException{
    public ProductExistsException(String message){
        super(message);
    }
}
