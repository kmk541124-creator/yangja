package me.kimmukwan.yungja.global.exception;

public record ExceptionResponse(
        ExceptionMessage status,
        String message
) {

    public static ExceptionResponse from(ExceptionMessage status, String message){
        return new ExceptionResponse(
                status,
                message
        );
    }
}
