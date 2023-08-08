package online.partyrun.partyrunauthenticationservice.domain.member.exception;

import online.partyrun.partyrunauthenticationservice.global.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(String id) {
        super(String.format("%s 아이디의 멤버를 찾을 수 없습니다.", id));
    }
}
