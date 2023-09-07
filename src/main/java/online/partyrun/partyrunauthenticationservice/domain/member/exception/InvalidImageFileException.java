package online.partyrun.partyrunauthenticationservice.domain.member.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class InvalidImageFileException extends BadRequestException {
    public InvalidImageFileException() {
        super("S3 저장 중 문제가 발생하였습니다.");
    }
}
