package com.vivace.rental_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*") // Cho phép ReactJS gọi sang
public class FileController {

    // Thư mục lưu ảnh gốc đã tạo ở Bước 1
    private final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Kiểm tra và tạo thư mục nếu chưa tồn tại
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 2. Lấy tên gốc của ảnh và trích xuất đuôi file (VD: .jpg, .png)
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 3. Tạo tên file mới bằng UUID để đảm bảo không bao giờ bị trùng tên
            String newFilename = UUID.randomUUID().toString() + extension;

            // 4. Copy file từ luồng mạng vào ổ cứng
            Path filePath = Paths.get(UPLOAD_DIR + newFilename);
            Files.write(filePath, file.getBytes());

            // 5. Trả về đường dẫn ảo để Frontend lưu vào cột image_url trong Database
            String fileUrl = "/uploads/" + newFilename;
            return ResponseEntity.ok(fileUrl);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Lỗi khi lưu file: " + e.getMessage());
        }
    }
}