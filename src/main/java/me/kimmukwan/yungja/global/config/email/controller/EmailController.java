package me.kimmukwan.yungja.global.config.email.controller;


import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.global.apiResponse.ApiResponse;
import me.kimmukwan.yungja.global.config.email.dto.EmailRequest;
import me.kimmukwan.yungja.global.config.email.dto.EmailResponse;
import me.kimmukwan.yungja.global.config.email.service.EmailService;
import me.kimmukwan.yungja.global.exception.CustomException;
import me.kimmukwan.yungja.global.exception.ExceptionMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/send")
    public ApiResponse<Void> send(@RequestBody String email){
        emailService.send(email);

        return ApiResponse.response(200, true, null, null);
    }

    @GetMapping("/verify")
    public ApiResponse<Void> verify(@RequestBody EmailRequest request){
        emailService.verify(request);

        return ApiResponse.response(200, true, null , null);
    }

    @GetMapping("/isverify")
    public ApiResponse<EmailResponse> isVerify(@RequestBody String email){
        Boolean isVerify = emailService.isVerify(email);

        if(!isVerify){
            throw new CustomException(ExceptionMessage.NOT_MATCH_EMAIL_CODE);
        }
        return ApiResponse.response(200, true, EmailResponse.from(isVerify), null);
    }
}
