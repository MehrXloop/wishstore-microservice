package com.mobilestore.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilestore.demo.model.Product;
import com.mobilestore.demo.repository.ProductRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository proRepo;

    @PostMapping(value = "")
    public String postProducts(@RequestBody Product product) {
        proRepo.save(product);
        return "Aagaya Product 😀";
    }

    @GetMapping(value = "/all")
    public List<Product> prodList() {
        return proRepo.findAll();
    }

    @GetMapping(value = "/get/{id}")
    public Product prod(@PathVariable Long id) {
        return proRepo.findById(id).orElse(null);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String prodDelete(@PathVariable Long id) {
        proRepo.deleteById(id);
        return "delete hou gia 😎";
    }

    @DeleteMapping(value = "/deleteAll")
    public String deletingAllProd() {
        proRepo.deleteAll();
        return "Ho gaya sab delete";
    }

}
