package com.ecommerce.shared_libs.response;

public class ApiResponse<T> {
    private boolean success;
    private final ErrorResponse error;
    private T data;

    // Constructor private (dùng factory method)
    private ApiResponse(boolean success, ErrorResponse error, T data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    public static <T> ApiResponse<T> success( T data) {
        return new ApiResponse<>(true,null,data);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false,new ErrorResponse(code, message),null);
    }

    public T data() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public ErrorResponse getError() {
        return error;
    }

    public record ErrorResponse(
            String code,
            String message
    ){}

}
