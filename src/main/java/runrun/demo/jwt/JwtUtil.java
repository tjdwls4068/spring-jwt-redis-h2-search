package runrun.demo.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 시크릿 키
    private static final String SECRET_KEY = "my-super-secret-key-that-is-very-long-and-secure-12345678";

    // 만료시간 설정 30분
    private static final long EXPIRATION_TIME = 1000 * 60 * 30;

    // 서명에 사용할 키 객체
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 토큰 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 사용자명
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME)) // 만료
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘
                .compact(); // 모든 설정 종합해서 String 만듬

    }

    // 토큰에서 사용자명 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)  // key 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody()
                .getSubject();  // username
    }

    // 토큰 유효성 검증
    public boolean validationToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);  // 여기서 에러 안나면 유효한 토큰
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }
}
