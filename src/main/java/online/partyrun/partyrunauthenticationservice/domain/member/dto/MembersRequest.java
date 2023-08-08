package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MembersRequest(@NotNull List<String> ids) {
}
