package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import jakarta.validation.constraints.NotNull;

public record MemberNameUpdateRequest(@NotNull String name) {
}
