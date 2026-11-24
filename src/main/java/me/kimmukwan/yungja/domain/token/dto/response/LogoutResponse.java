package me.kimmukwan.yungja.domain.token.dto.response;

import org.springframework.http.ResponseCookie;

public record LogoutResponse(
        ResponseCookie cookie,
        String message
) {

    public static LogoutResponse from(ResponseCookie cookie, String message){
        return new LogoutResponse(
                cookie,
                message
        );
    }
}
