package me.kimmukwan.yungja.domain.token.dto.response;

public record CreateTokenResponse(
        String accessToken
) {

    public static CreateTokenResponse from(String accessToken){
        return new CreateTokenResponse(accessToken);
    }
}
