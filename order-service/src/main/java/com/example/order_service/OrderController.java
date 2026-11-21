package com.example.order_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final RestClient restClient;

    public OrderController() {
        this.restClient = RestClient.builder()
            .baseUrl("http://localhost:8081")
            .build();
    }

    public OrderController(RestClient.Builder builder) {
        this.restClient = (builder != null ? builder : RestClient.builder())
            .baseUrl("http://localhost:8081")
            .build();
    }

    // API lấy danh sách đơn hàng mẫu (Câu C)
    @GetMapping
    public List<Map<String, Object>> getOrders() {
        return List.of(
            Map.of("id", 101, "productId", 1, "quantity", 2),
            Map.of("id", 102, "productId", 2, "quantity", 1)
        );
    }

    // API giao tiếp với Product Service qua RestClient (Câu D)
    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getProduct(@PathVariable int id) {
        try {
            String productJson = restClient.get()
                .uri("/api/products/{id}", id)
                .retrieve()
                .body(String.class);
            return ResponseEntity.ok(productJson);
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"message\": \"Không tìm thấy sản phẩm\"}");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
