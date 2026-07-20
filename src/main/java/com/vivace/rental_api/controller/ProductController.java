package com.vivace.rental_api.controller;

import com.vivace.rental_api.entity.Product;
import com.vivace.rental_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products") // Đường dẫn để gọi Lễ tân này
@CrossOrigin(origins = "*") // Rất quan trọng: Cho phép Frontend (Figma/React) lấy được dữ liệu mà không bị chặn bảo mật
public class ProductController {

    @Autowired
    private ProductService productService; // Gọi Quản lý lên làm việc

    // Khi khách hàng truy cập bằng phương thức GET
    @GetMapping
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // Sửa productService.save(...) thành productService.saveProduct(...)
        Product savedProduct = productService.saveProduct(product); 
        return ResponseEntity.ok(savedProduct);
    }
}