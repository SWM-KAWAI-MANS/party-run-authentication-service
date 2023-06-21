package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import java.util.Set;

public record MemberResponse(String id, String authId, String name, Set<String> roles) {}
