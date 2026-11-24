package me.kimmukwan.yungja.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final StringRedisTemplate redisTemplate;

    private static final String REFRESH_PREFIX = "refresh:";

    public boolean isSaved(Long userId, String refreshToken){
        if(userId == null || refreshToken == null){
            return false;
        }
        String savedToken = redisTemplate.opsForValue().get(REFRESH_PREFIX + userId);
        return refreshToken.equals(savedToken);
    }

    public void save(Long userId, String newRefreshToken){
        redisTemplate.opsForValue().set(REFRESH_PREFIX + userId, newRefreshToken, TokenService.REFRESH_TOKEN_DURATION);
    }

    public void delete(Long userId){
        if(userId == null){
            return;
        }
        redisTemplate.delete(REFRESH_PREFIX + userId);
    }
}
