package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;

import java.util.List;

public record MembersResponse(List<MemberResponse> members) {

    public static MembersResponse from(List<Member> members) {
        return new MembersResponse(
                members.stream()
                        .map(MemberResponse::new)
                        .toList()
        );
    }
}
