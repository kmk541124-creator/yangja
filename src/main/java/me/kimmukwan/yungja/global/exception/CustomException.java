package me.kimmukwan.yungja.global.exception;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ExceptionMessage exceptionMessage;
    private String detailMessage;

    public CustomException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getDescription());
        this.exceptionMessage = exceptionMessage;
        this.detailMessage = exceptionMessage.getDescription();
    }
}
