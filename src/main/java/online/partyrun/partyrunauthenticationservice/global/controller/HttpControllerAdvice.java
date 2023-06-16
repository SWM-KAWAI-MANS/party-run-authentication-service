package online.partyrun.partyrunauthenticationservice.global.controller;

import lombok.extern.slf4j.Slf4j;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;
import online.partyrun.partyrunauthenticationservice.global.exception.InternalServerErrorException;
import online.partyrun.partyrunauthenticationservice.global.exception.UnAuthorizedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HttpControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(
            BadRequestException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse("잘못된 요청입니다."));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnAuthorizedException(
            UnAuthorizedException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponse("승인되지 않은 요청입니다."));
    }

    @ExceptionHandler({RuntimeException.class, InternalServerErrorException.class, Exception.class})
    public ResponseEntity<ExceptionResponse> handleInternalServerErrorException(
            Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(new ExceptionResponse("알 수 없는 에러입니다."));
    }

    @ExceptionHandler({BindException.class, MissingRequestHeaderException.class})
    public ResponseEntity<ExceptionResponse> handleBindException(BindException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse("질못된 요청입니다"));
    }
}
