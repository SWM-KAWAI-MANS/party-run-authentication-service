package online.partyrun.partyrunauthenticationservice.domain.member.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class InvalidMemberProfileException extends BadRequestException {
    public InvalidMemberProfileException(String value) {
        super(String.format("멤버의 프로필은 %s 일 수 없습니다.", value));
    }
}
