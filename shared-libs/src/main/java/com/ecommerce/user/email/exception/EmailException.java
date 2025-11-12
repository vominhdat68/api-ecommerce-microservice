package com.ecommerce.user.email.exception;

//RuntimeException Ngoại lệ được xử lý trước khi gọi phương thức thực thi
public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
