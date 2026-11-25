package me.kimmukwan.yungja.global.config;


import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.global.apiResponse.ApiResponse;
import me.kimmukwan.yungja.global.config.jwt.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import me.kimmukwan.yungja.global.config.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    @Value("${cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Value("${cookie.refresh-token.secure}")
    private boolean cookieSecure;

    @Value("${cookie.refresh-token.same-site}")
    private String cookieSameSite;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/signup",
                                "/api/token",
                                "/logout",
                                "/api/email/**").permitAll().anyRequest().authenticated()).exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, e) ->
                                ApiResponse.response(401, false, "로그인이 필요합니다", e))
                        .accessDeniedHandler(((request, response, e) ->
                                ApiResponse.response(403, false, "CSRF 토큰이 없거나 올바르지 않습니다", e))))
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
