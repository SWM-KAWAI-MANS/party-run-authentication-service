package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;

public record MemberAuthRequest(String authId, String name) {

    public Member toEntity() {
        return new Member(authId, name);
    }
}
