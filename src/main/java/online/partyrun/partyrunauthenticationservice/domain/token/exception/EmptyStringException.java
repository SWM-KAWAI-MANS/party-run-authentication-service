package online.partyrun.partyrunauthenticationservice.domain.token.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class EmptyStringException extends BadRequestException {
    public EmptyStringException(String str) {
        super(str + "은 빈 값일 수 없습니다.");
    }
}
