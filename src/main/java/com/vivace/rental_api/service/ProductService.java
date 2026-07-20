package com.vivace.rental_api.service;

import com.vivace.rental_api.entity.Product;
import com.vivace.rental_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository; // Gọi Thủ kho lên làm việc

    // Hàm lấy toàn bộ danh sách váy
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Lệnh này tương đương với: SELECT * FROM products;
    }
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}