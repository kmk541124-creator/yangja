package me.kimmukwan.yungja.domain.auth.dto.request;

public record SignupRequest(
        String email,
        String username,
        String password
) {

    public static SignupRequest from(String email, String username, String password){
        return new SignupRequest(
                email, username, password);
    }
}
