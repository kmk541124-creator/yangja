package me.kimmukwan.yungja.global.config.email.service;


import lombok.RequiredArgsConstructor;
import me.kimmukwan.yungja.global.config.email.dto.EmailRequest;
import me.kimmukwan.yungja.global.config.email.dto.EmailResponse;
import me.kimmukwan.yungja.global.exception.CustomException;
import me.kimmukwan.yungja.global.exception.ExceptionMessage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final String CODE_PREFIX = "email:code:";
    private static final String VERIFIED_PREFIX = "email:verified:";
    private static final Duration CODE_DURATION = Duration.ofMinutes(5);
    private static final Duration VERIFIED_DURATION = Duration.ofMinutes(30);

    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    private final SecureRandom random = new SecureRandom();

    public void send(String email){
        String code = String.format("%06d", random.nextInt(1_000_000));
        redisTemplate.opsForValue().set(CODE_PREFIX + email, code, CODE_DURATION);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[yungja] 이메일 인증번호");
        message.setText("인증번호 " + code + "\n5분안에 입력해주세요");
        mailSender.send(message);
    }

    public void verify(EmailRequest request){
        String redisCode = redisTemplate.opsForValue().get(CODE_PREFIX + request.email());

        if(redisCode == null){
            throw new CustomException(ExceptionMessage.NOT_FOUND_EMAIL_CODE);
        }

        if(redisCode.equals(request.code())){
            redisTemplate.delete(CODE_PREFIX + request.email());
            redisTemplate.opsForValue().set(VERIFIED_PREFIX + request.email(), "true", VERIFIED_DURATION);
        } else{
            throw new CustomException(ExceptionMessage.NOT_MATCH_EMAIL_CODE);
        }
    }

    public Boolean isVerify(String email){
        return Boolean.TRUE.equals(redisTemplate.hasKey(VERIFIED_PREFIX+email));
    }

    public void clearVerified(String email) {
        redisTemplate.delete(VERIFIED_PREFIX + email);
    }
}
