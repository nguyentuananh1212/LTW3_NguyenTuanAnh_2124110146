# Hướng Dẫn Chạy & Báo Cáo Thực Hành Lab 01
**Môn học:** Lập trình Web 3 – Spring Boot & Microservices  
**Đề tài:** LAB 01 – KHỞI TẠO VÀ GIAO TIẾP GIỮA CÁC MICROSERVICES (Câu A - Câu F)

---

## 1. Nội Dung Yêu Cầu Đã Thực Hiện

Dự án hoàn thành đầy đủ tất cả các yêu cầu từ **Câu A đến Câu F** theo tài liệu hướng dẫn:

- **Câu A:** Kiểm tra môi trường lập trình Java, Spring Boot, VS Code, Postman.
- **Câu B:** Khởi tạo 2 Spring Boot Services: `product-service` (cổng 8081) và `order-service` (cổng 8082).
- **Câu C:** Xây dựng REST API cơ bản cho từng service:
  - `Product Service`: `GET /api/products`, `GET /api/products/{id}`
  - `Order Service`: `GET /api/orders`
- **Câu D:** Giao tiếp giữa các Microservices bằng `RestClient`:
  - `Order Service` cung cấp API `GET /api/orders/product/{id}` để gọi sang `Product Service` tại `http://localhost:8081/api/products/{id}`.
- **Câu E:** Quản lý mã nguồn với Git, thiết lập `.gitignore`, commit và quản lý repository.
- **Câu F:** Kết hợp Frontend cơ bản với Microservices:
  - Cấu hình `@CrossOrigin` cho phép Frontend gọi API qua trình duyệt.
  - Xây dựng giao diện web `frontend/index.html` với đầy đủ chức năng:
    - Hiển thị danh sách sản phẩm lấy từ `Product Service` (8081).
    - Tìm kiếm sản phẩm thông qua `Order Service` (8082).
  - Hoàn thiện API tìm kiếm sản phẩm theo ID (`GET /api/products/{id}`) trả về HTTP 200 OK hoặc HTTP 404 Not Found.

---

## 2. Cấu Trúc Dự Án

```
lab01-microservices/
├── product-service/                     (Microservice quản lý sản phẩm - Cổng 8081)
│   ├── src/main/java/com/example/product_service/
│   │   ├── ProductController.java       (APIs: GET /api/products, GET /api/products/{id})
│   │   └── ProductServiceApplication.java
│   └── pom.xml
├── order-service/                       (Microservice quản lý đơn hàng - Cổng 8082)
│   ├── src/main/java/com/example/order_service/
│   │   ├── OrderController.java         (APIs: GET /api/orders, GET /api/orders/product/{id} qua RestClient)
│   │   └── OrderServiceApplication.java
│   └── pom.xml
├── frontend/
│   └── index.html                       (Giao diện web kiểm thử cả 2 service theo Câu F)
├── run-all.bat                          (Script khởi động nhanh cả 2 service)
├── .gitignore
└── README.md
```

---

## 3. Cách Chạy Các Microservices

### Cách 1: Chạy nhanh bằng file `run-all.bat` (Khuyên dùng trên Windows)
- Nhấp đúp chuột vào file `run-all.bat` trong thư mục `lab01-microservices`.
- Script sẽ tự động mở 2 cửa sổ Command Prompt riêng biệt cho:
  - **Product Service** trên cổng `8081`
  - **Order Service** trên cổng `8082`

### Cách 2: Chạy bằng Terminal trong VS Code
Mở 2 Terminal riêng biệt trong VS Code:

- **Terminal 1 – Product Service:**
  ```bash
  cd product-service
  mvn spring-boot:run
  ```
  *(Service khởi động tại `http://localhost:8081`)*

- **Terminal 2 – Order Service:**
  ```bash
  cd order-service
  mvn spring-boot:run
  ```
  *(Service khởi động tại `http://localhost:8082`)*

---

## 4. Cách Kiểm Thử (Testing)

### 4.1. Kiểm Thử Bằng Giao Diện Frontend (Câu F)
- Chuột phải vào file `frontend/index.html` chọn **Open with Live Server** (hoặc mở trực tiếp file `index.html` bằng trình duyệt web).

**Các kịch bản kiểm thử (Trang 10 - 11):**
1. **Tải danh sách sản phẩm:** Nhấn nút **"Tải danh sách sản phẩm"** -> Danh sách sản phẩm được lấy trực tiếp từ `Product Service` (Port 8081) và hiển thị lên giao diện.
2. **Tìm sản phẩm theo ID:**
   - Nhập `ID = 1`, nhấn **"Tìm sản phẩm"** -> `Order Service` (Port 8082) gọi sang `Product Service` (Port 8081) và hiển thị thông tin sản phẩm có ID = 1 dưới dạng JSON.
   - Nhập `ID không tồn tại` (ví dụ: `9999`) -> Hiển thị thông báo *"Không tìm thấy sản phẩm"* khi API trả về 404.
   - Để trống ID hoặc nhập `0`, `-1` -> Hiển thị thông báo yêu cầu nhập ID hợp lệ.
3. **Kiểm tra khi tắt service:**
   - Tắt `Product Service` (8081), thử tải danh sách -> Hiển thị thông báo lỗi khi không kết nối được API.
   - Tắt `Order Service` (8082), thử tìm sản phẩm -> Hiển thị thông báo lỗi khi không kết nối được API.

### 4.2. Kiểm Thử REST API Bằng Postman / Trình Duyệt
| Phương thức | Endpoint | Service | Mô tả | Kết quả mong đợi |
|-------------|----------|---------|-------|-------------------|
| `GET` | `http://localhost:8081/api/products` | Product Service | Lấy danh sách sản phẩm | 200 OK, JSON danh sách sản phẩm |
| `GET` | `http://localhost:8081/api/products/1` | Product Service | Lấy sản phẩm có ID = 1 | 200 OK, JSON sản phẩm ID 1 |
| `GET` | `http://localhost:8081/api/products/9999` | Product Service | Lấy sản phẩm không tồn tại | 404 Not Found |
| `GET` | `http://localhost:8082/api/orders` | Order Service | Lấy danh sách đơn hàng | 200 OK, JSON đơn hàng |
| `GET` | `http://localhost:8082/api/orders/product/1` | Order Service | Order Service gọi Product Service | 200 OK, JSON sản phẩm ID 1 |
