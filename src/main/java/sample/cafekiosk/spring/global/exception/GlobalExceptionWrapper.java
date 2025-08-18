package sample.cafekiosk.spring.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sample.cafekiosk.spring.global.api.CommonResponse;


@RestControllerAdvice
public class GlobalExceptionWrapper {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public CommonResponse bindException(BindException e){
        return CommonResponse.of(e,
                e.getBindingResult()
                        .getFieldErrors() // 필드 에러만 우선
                        .stream()
                        .findFirst()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .orElse("there is no default message"),
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
}
