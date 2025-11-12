package com.ecommerce.user.response;

public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    // Constructor private (dùng factory method)
    private ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null,null,data);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false,code,message,null);
    }

    public T data() {
        return data;
    }
    public String message() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }


}
