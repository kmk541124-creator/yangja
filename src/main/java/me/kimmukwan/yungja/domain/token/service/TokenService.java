package me.kimmukwan.yungja.domain.token.service;


import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.domain.user.entity.User;
import me.kimmukwan.yungja.domain.user.repository.UserRepository;
import me.kimmukwan.yungja.global.config.jwt.TokenProvider;
import me.kimmukwan.yungja.global.exception.CustomException;
import me.kimmukwan.yungja.global.exception.ExceptionMessage;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2);

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public String createNewAccessToken(String refreshToken){
        if(refreshToken == null || !tokenProvider.validToken(refreshToken, TokenProvider.TYPE_REFRESH)){
            throw new IllegalArgumentException("유효하지 않거나 만료된 리프레시 토큰입니다");
        }

        Long userId = tokenProvider.getUserId(refreshToken);
        if(!refreshTokenService.isSaved(userId, refreshToken)){
            throw new IllegalArgumentException("유효하지 않거나 만료된 리프레시 토큰입니다");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionMessage.NOT_FOUND_USER_ID));

        return tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION, TokenProvider.TYPE_ACCESS);
    }

    public void logout(String refreshToken, Long userId){
        if(userId == null && refreshToken != null && tokenProvider.validToken(refreshToken, TokenProvider.TYPE_REFRESH)){
            userId = tokenProvider.getUserId(refreshToken);
        }
        refreshTokenService.delete(userId);
    }
}
