package me.kimmukwan.yungja.domain.token.dto.request;

public record CreateAccessTokenRequest(
        String refreshToken
) {
}
