package online.partyrun.partyrunauthenticationservice.global.controller;

import lombok.extern.slf4j.Slf4j;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;
import online.partyrun.partyrunauthenticationservice.global.exception.InternalServerErrorException;
import online.partyrun.partyrunauthenticationservice.global.exception.NotFoundException;
import online.partyrun.partyrunauthenticationservice.global.exception.UnAuthorizedException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HttpControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(BadRequestException exception) {
        log.warn(exception.getMessage());
        return new ExceptionResponse("잘못된 요청입니다.");
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleUnAuthorizedException(UnAuthorizedException exception) {
        log.warn(exception.getMessage());
        return new ExceptionResponse("승인되지 않은 요청입니다.");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException exception) {
        log.warn(exception.getMessage());
        return new ExceptionResponse("요청한 리소스를 찾을 수 없습니다.");
    }

    @ExceptionHandler({RuntimeException.class, InternalServerErrorException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalServerErrorException(Exception exception) {
        log.error(exception.getMessage());
        return new ExceptionResponse("알 수 없는 에러입니다.");
    }

    @ExceptionHandler({BindException.class, MissingRequestHeaderException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBindException(BindException exception) {
        log.error(exception.getMessage());
        return new ExceptionResponse("질못된 요청입니다");
    }
}
