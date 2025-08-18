package sample.cafekiosk.spring.global.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Getter
public class CommonResponse<T> {
    private String message;
    private int code;
    private HttpStatus status;
    private T data;

    public static <T> CommonResponse<T> of(T data, String message, int code, HttpStatus status) {
        return CommonResponse.<T>builder()
                .message(message)
                .code(code)
                .status(status)
                .data(data)
                .build();
    }
}
