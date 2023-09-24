package online.partyrun.partyrunauthenticationservice.domain.member.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.BadRequestException;

public class InvalidMemberNameException extends BadRequestException {

    public InvalidMemberNameException() {
        super("Member의 이름은 빈 값일 수 없습니다.");
    }
}
