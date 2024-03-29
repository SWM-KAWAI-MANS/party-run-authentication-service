package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Role;

import java.util.Set;

public record MemberAuthResponse(String id, String authId, String name, Set<Role> roles) {

    public MemberAuthResponse(Member member) {
        this(member.getId(), member.getAuthId(), member.getName(), member.getRoles());
    }
}
