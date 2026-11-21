package com.example.product_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Danh sách dữ liệu mẫu theo yêu cầu Câu C & Câu F.4
    private final List<Map<String, Object>> products = List.of(
        Map.of("id", 1, "name", "Mouse", "price", 150000),
        Map.of("id", 2, "name", "Keyboard", "price", 250000),
        Map.of("id", 3, "name", "Monitor", "price", 2500000)
    );

    // API lấy toàn bộ danh sách sản phẩm (Câu C - trang 3, 4)
    @GetMapping
    public List<Map<String, Object>> getProducts() {
        return products;
    }

    // API tìm kiếm sản phẩm theo ID (Câu F.4 - trang 10)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable int id) {
        return products.stream()
            .filter(p -> Objects.equals(p.get("id"), id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
