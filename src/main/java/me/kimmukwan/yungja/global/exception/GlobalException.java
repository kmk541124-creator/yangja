package me.kimmukwan.yungja.global.exception;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class GlobalException {

    @ExceptionHandler(CustomException.class)
    public ExceptionResponse handleException(
            CustomException e
    ){
        return ExceptionResponse.from(e.getExceptionMessage(), e.getDetailMessage());
    }
}
