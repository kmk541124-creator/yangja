package me.kimmukwan.yungja.domain.token.controller;

import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.domain.token.dto.request.CreateAccessTokenRequest;
import me.kimmukwan.yungja.domain.token.dto.response.CreateTokenResponse;
import me.kimmukwan.yungja.domain.token.dto.response.LogoutResponse;
import me.kimmukwan.yungja.domain.token.service.TokenService;
import me.kimmukwan.yungja.domain.user.entity.User;
import me.kimmukwan.yungja.global.apiResponse.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenService tokenService;

    @Value("${cookie.refresh-token.secure}")
    private boolean cookieSecure;

    @Value("${cookie.refresh-token.same-site}")
    private String cookieSameSite;

    @PostMapping("/api/token")
    public ApiResponse<CreateTokenResponse> createNewAccessToken(
            @CookieValue(value = TokenService.REFRESH_TOKEN_COOKIE_NAME, required = false) String cookieToken,
            @RequestBody(required = false)CreateAccessTokenRequest request
            ){
        String refreshToken = cookieToken != null ? cookieToken : (request != null ? request.refreshToken() : null);
        String newAccessToken = tokenService.createNewAccessToken(refreshToken);

        return ApiResponse.response(201, true, CreateTokenResponse.from(newAccessToken), null);
    }


    @PostMapping("/logout")
    public ApiResponse<LogoutResponse> logout(
            @CookieValue(value = TokenService.REFRESH_TOKEN_COOKIE_NAME, required = false) String cookieToken,
            @AuthenticationPrincipal User user){
        tokenService.logout(cookieToken, user != null ? user.getId() : null);

        ResponseCookie cookie = ResponseCookie.from(TokenService.REFRESH_TOKEN_COOKIE_NAME, "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .build();

        LogoutResponse logoutResponse = LogoutResponse.from(cookie, "로그아웃했습니다");
        return ApiResponse.response(200, true, logoutResponse, null);
    }
}
