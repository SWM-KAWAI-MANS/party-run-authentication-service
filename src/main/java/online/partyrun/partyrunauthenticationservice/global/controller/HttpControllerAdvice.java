package online.partyrun.partyrunauthenticationservice.global.controller;

import lombok.extern.slf4j.Slf4j;
import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;
import online.partyrun.partyrunauthenticationservice.global.exception.NotFoundException;
import online.partyrun.partyrunauthenticationservice.global.logging.RequestLogFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class HttpControllerAdvice {

    private static final String BAD_REQUEST_MESSAGE = "잘못된 요청입니다.";
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "요청한 리소스를 찾을 수 없습니다.";
    private static final String SERVER_ERROR_MESSAGE = "알 수 없는 에러입니다.";
    private static final String EXCEPTION_MESSAGE = "[EXCEPTION]";


    @ExceptionHandler({
            BadRequestException.class,
            HttpMessageNotReadableException.class,
            MissingRequestHeaderException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(Exception exception) {
        log.warn("{} {}", EXCEPTION_MESSAGE, exception.getMessage());
        return new ExceptionResponse(BAD_REQUEST_MESSAGE);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBindException(BindException exception) {
        final String message =
                exception.getBindingResult().getAllErrors().stream()
                        .map(error -> String.format("%s: %s", ((FieldError) error).getField(), error.getDefaultMessage()))
                        .collect(Collectors.joining(", "));

        log.warn("{} {}",EXCEPTION_MESSAGE, message);
        return new ExceptionResponse(BAD_REQUEST_MESSAGE);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException exception) {
        log.warn("{} {}", EXCEPTION_MESSAGE, exception.getMessage());
        return new ExceptionResponse(NOT_FOUND_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalServerErrorException(Exception exception, ContentCachingRequestWrapper request) {
        log.error("{} {} \n {}", EXCEPTION_MESSAGE, exception.getMessage(), RequestLogFormatter.toPrettyRequestString(request));
        return new ExceptionResponse(SERVER_ERROR_MESSAGE);
    }
}
