package me.kimmukwan.yungja.global.config.email.dto;

public record EmailResponse(
        Boolean verifySuccess
) {

    public static EmailResponse from(Boolean verifySuccess){
        return new EmailResponse(verifySuccess);
    }
}
