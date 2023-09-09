package online.partyrun.partyrunauthenticationservice.domain.member.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class InvalidFileStreamException extends BadRequestException {
    public InvalidFileStreamException() {
        super("파일 스트림 형식이 잘못되었습니다.");
    }
}
