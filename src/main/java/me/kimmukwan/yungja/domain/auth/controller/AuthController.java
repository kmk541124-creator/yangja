package me.kimmukwan.yungja.domain.auth.controller;


import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.domain.auth.dto.request.LoginRequest;
import me.kimmukwan.yungja.domain.auth.dto.request.SignupRequest;
import me.kimmukwan.yungja.domain.auth.dto.response.LoginResponse;
import me.kimmukwan.yungja.domain.auth.service.AuthService;
import me.kimmukwan.yungja.domain.token.service.TokenService;
import me.kimmukwan.yungja.domain.user.entity.User;
import me.kimmukwan.yungja.global.apiResponse.ApiResponse;
import me.kimmukwan.yungja.global.config.email.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final EmailService emailService;

    @Value("${cookie.refresh-token.secure}")
    private boolean cookieSecure;

    @Value("${cookie.refresh-token.same-site}")
    private String cookieSameSite;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@RequestBody SignupRequest request){
        authService.signup(request);

        emailService.isVerify(request.email());

        return ApiResponse.response(201, true, null, null);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);

        ResponseCookie responseCookie = ResponseCookie.from(tokenService.REFRESH_TOKEN_COOKIE_NAME, response.refreshToken())
                .path("/")
                .maxAge(TokenService.REFRESH_TOKEN_DURATION)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(response);
    }
}
