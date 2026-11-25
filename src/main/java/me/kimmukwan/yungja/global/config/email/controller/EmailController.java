package me.kimmukwan.yungja.global.config.email.controller;


import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.global.apiResponse.ApiResponse;
import me.kimmukwan.yungja.global.config.email.dto.EmailRequest;
import me.kimmukwan.yungja.global.config.email.dto.EmailResponse;
import me.kimmukwan.yungja.global.config.email.service.EmailService;
import me.kimmukwan.yungja.global.exception.CustomException;
import me.kimmukwan.yungja.global.exception.ExceptionMessage;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ApiResponse<Void> send(@RequestBody String email){
        emailService.send(email);

        return ApiResponse.response(200, true, null, null);
    }

    @PostMapping("/verify")
    public ApiResponse<Void> verify(@RequestBody EmailRequest request){
        emailService.verify(request);

        return ApiResponse.response(200, true, null , null);
    }

    @PostMapping("/isverify")
    public ApiResponse<EmailResponse> isVerify(@RequestBody String email){
        Boolean isVerify = emailService.isVerify(email);

        if(!isVerify){
            throw new CustomException(ExceptionMessage.NOT_MATCH_EMAIL_CODE);
        }
        return ApiResponse.response(200, true, EmailResponse.from(isVerify), null);
    }
}
