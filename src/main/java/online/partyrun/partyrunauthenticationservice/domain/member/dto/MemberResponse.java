package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Role;

import java.util.Set;

public record MemberResponse(String id, String authId, String name, Set<Role> roles) {}
