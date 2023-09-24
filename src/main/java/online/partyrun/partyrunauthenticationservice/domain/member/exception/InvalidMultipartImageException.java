package online.partyrun.partyrunauthenticationservice.domain.member.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class InvalidMultipartImageException extends BadRequestException {
    public InvalidMultipartImageException() {
        super("multipart image file 형식이 잘못되었습니다.");
    }

}
