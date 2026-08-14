📌 Giới thiệu

**LMS Platform** là hệ thống RESTful API của nền tảng **LMS Platform**, được xây dựng bằng **Spring Boot**. Backend chịu trách nhiệm xử lý nghiệp vụ, xác thực người dùng, quản lý dữ liệu và cung cấp API cho ứng dụng Frontend.

Hệ thống được thiết kế theo kiến trúc nhiều lớp (Layered Architecture), giúp dễ dàng mở rộng, bảo trì và phát triển thêm các tính năng trong tương lai.

Các phân hệ chính:

- **Authentication**: Đăng ký, đăng nhập và xác thực bằng JWT.
- **User Management**: Quản lý thông tin người dùng và phân quyền.
- **Course Management**: Quản lý khóa học, chương học và bài giảng.
- **Enrollment**: Đăng ký và quản lý học viên tham gia khóa học.
- **Quiz Management**: Quản lý bài kiểm tra, câu hỏi và kết quả.
- **Dashboard & Statistics**: Thống kê dữ liệu phục vụ quản trị hệ thống.

---

🚀 Cách chạy dự án

1. Cài đặt **Java JDK 23** và **Maven**.
2. Clone repository và di chuyển vào thư mục backend.
3. Tạo cơ sở dữ liệu MySQL và cấu hình thông tin kết nối trong `application.yml` hoặc `application.properties`.
4. Chạy ứng dụng:

```bash
mvn clean install
mvn spring-boot:run
```

Mặc định ứng dụng chạy tại:

```text
http://localhost:8080
```

Swagger API:

```text
http://localhost:8080/swagger-ui/index.html
```

---

🛠️ Công nghệ sử dụng

- Java 23
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- Lombok
- MapStruct
- Swagger / OpenAPI

---

📌 Kiến trúc dự án

Backend được tổ chức theo kiến trúc nhiều lớp:

- **Controller**: Tiếp nhận và xử lý các HTTP Request.
- **Service**: Chứa nghiệp vụ của hệ thống.
- **Repository**: Làm việc với cơ sở dữ liệu thông qua Spring Data JPA.
- **Entity**: Ánh xạ các bảng trong cơ sở dữ liệu.
- **DTO**: Trao đổi dữ liệu giữa các tầng.
- **Security**: Xử lý xác thực và phân quyền bằng JWT.
- **Configuration**: Cấu hình hệ thống và các Bean.

---

📌 Ghi chú

- Toàn bộ API đều được xây dựng theo chuẩn RESTful.
- Xác thực người dùng bằng JWT.
- Cấu hình cơ sở dữ liệu và các thông số môi trường thông qua file cấu hình của Spring Boot.
- Có thể dễ dàng tích hợp với Frontend hoặc ứng dụng di động thông qua REST API.

---

📌 License

Dự án được phát triển phục vụ mục đích học tập và nghiên cứu.
