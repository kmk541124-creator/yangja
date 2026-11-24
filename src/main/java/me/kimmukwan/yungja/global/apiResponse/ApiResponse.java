package me.kimmukwan.yungja.global.apiResponse;


import jakarta.annotation.Nullable;

public record ApiResponse<T>(
        int status,
        boolean success,
        @Nullable
        T data,
        @Nullable
        Exception error
) {

    public static <T> ApiResponse<T> response(int status, boolean success, T data, Exception e){
        return new ApiResponse<>(
                status,
                success,
                data,
                e
        );
    }
}
