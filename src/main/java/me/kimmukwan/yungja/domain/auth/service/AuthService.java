package me.kimmukwan.yungja.domain.auth.service;


import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.domain.auth.dto.request.LoginRequest;
import me.kimmukwan.yungja.domain.auth.dto.request.SignupRequest;
import me.kimmukwan.yungja.domain.auth.dto.response.LoginResponse;
import me.kimmukwan.yungja.domain.token.service.RefreshTokenService;
import me.kimmukwan.yungja.domain.token.service.TokenService;
import me.kimmukwan.yungja.domain.user.entity.CustomUserDetail;
import me.kimmukwan.yungja.domain.user.entity.User;
import me.kimmukwan.yungja.domain.user.repository.UserRepository;
import me.kimmukwan.yungja.global.config.jwt.TokenProvider;
import me.kimmukwan.yungja.global.exception.CustomException;
import me.kimmukwan.yungja.global.exception.ExceptionMessage;
import me.kimmukwan.yungja.global.exception.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private UserRepository userRepository;
    private GlobalException globalException;
    private PasswordEncoder passwordEncoder;

    public void signup(SignupRequest signupRequest){
        Optional<User> user = userRepository.findByUsername(signupRequest.username());
        if(user.isPresent()){
            throw new CustomException(ExceptionMessage.SAME_USERNAME);
        }
        if(signupRequest.username().isEmpty() || signupRequest.email().isEmpty() || signupRequest.password().isEmpty()){
            throw new CustomException(ExceptionMessage.NULL_SIGNUP);
        }

        String password = passwordEncoder.encode(signupRequest.password());

        User user1 = User.builder()
                .email(signupRequest.email()).username(signupRequest.username()).password(password).build();

        userRepository.save(user1);
    }

    public LoginResponse login(LoginRequest request){
        if(request.password().isEmpty() || request.username().isEmpty()){
            throw new CustomException(ExceptionMessage.NULL_LOGIN);
        }
        Optional<User> user = userRepository.findByUsername(request.username());
        if(user.isEmpty()){
            throw new CustomException(ExceptionMessage.NOT_FOUND);
        }
        CustomUserDetail customUserDetail = new CustomUserDetail(user.get());
        if(passwordEncoder.matches(request.password(),customUserDetail.getPassword())){
            String refreshToken = tokenProvider.generateToken(user.get(), TokenService.REFRESH_TOKEN_DURATION, TokenProvider.TYPE_REFRESH);
            refreshTokenService.save(user.get().getId(), refreshToken);
            String accessToken = tokenProvider.generateToken(user.get(), TokenService.ACCESS_TOKEN_DURATION, TokenProvider.TYPE_ACCESS);

            return LoginResponse.from(accessToken, refreshToken);
        }
        throw new CustomException(ExceptionMessage.NOT_EQUAL_PASSWORD);
    }
}
