package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;

public record MemberResponse(String id, String name, String profile) {

    public MemberResponse(Member member) {
        this(member.getId(), member.getName(), member.getProfile());
    }
}
