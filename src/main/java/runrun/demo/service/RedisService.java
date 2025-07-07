package runrun.demo.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> ops;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.ops = redisTemplate.opsForValue();
    }

    // ✅ JWT 저장 (로그인 시)
    public void saveToken(String token, long expirationTimeinMillis) {
        redisTemplate.opsForValue().set(token,"valid",expirationTimeinMillis, TimeUnit.MILLISECONDS);
    }

    // ✅ JWT 삭제 (로그아웃 시)
    /*public void deleteToken(String token) {
        redisTemplate.delete(token);
    }*/

    // ✅ JWT 존재 여부 확인 (요청 시)
    public boolean isTokenValid(String token) {
        return redisTemplate.hasKey(token);
    }

    public void blacklistIsToken(String token, long expiration) {
        ops.set("BL:" + token, "blacklisted", 30, TimeUnit.MINUTES);
    }

    public boolean isBlackListed(String token) {
        return redisTemplate.hasKey("BL:" + token);
    }

}
