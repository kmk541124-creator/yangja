package me.kimmukwan.yungja.global.exception;

public enum ExceptionMessage {

    SAME_USERNAME("이미 존재하는 닉네임 입니다"),
    NULL_SIGNUP("아이디, 비밀번호, 이메일의 값을 제대로 입력해주세요"),
    NULL_LOGIN("아이디, 비밀번호의 값을 제대로 입력해주세요"),
    NOT_EQUAL_PASSWORD("비밀번호가 일치하지 않습니다"),
    NOT_FOUND_NICKNAME("존재하지 않는 닉네임입니다"),
    TOKEN_EXPIRE("토큰이 만료되었습니다"),
    NOT_FOUND_USER_ID("존재하지 않는 유저입니다"),
    NOT_MATCH_EMAIL_CODE("코드가 일치하지 않습니다"),
    NOT_FOUND_EMAIL_CODE("오류가 발생했습니다 다시 발송해주세요"),
    NOT_INFO_CHAT_NAME("올바른 값을 입력해주세요"),
    NOT_FOUND("찾을 수 없습니다");

    private final String description;

    ExceptionMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
