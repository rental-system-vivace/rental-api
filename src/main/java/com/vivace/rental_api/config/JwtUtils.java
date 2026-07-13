package com.vivace.rental_api.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // Chuỗi ký tự bí mật dùng để ký nhận mã token (Phải đủ dài bảo mật)
    private final String jwtSecret = "SecretKeyForVivaceDressRentalApiProject2026SuperLongStringForSecurity";
    private final int jwtExpirationMs = 86400000; // Thời hạn của thẻ: 1 ngày (tính bằng mili giây)

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // 1. Hàm tạo mã Token khi đăng nhập thành công
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Hàm bóc tách lấy Email từ chuỗi Token gửi lên
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 3. Hàm kiểm tra xem Token còn hạn và hợp lệ không
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token bị chỉnh sửa, giả mạo hoặc hết hạn sử dụng
        }
        return false;
    }
}