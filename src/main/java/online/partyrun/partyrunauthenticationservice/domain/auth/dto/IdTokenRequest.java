package online.partyrun.partyrunauthenticationservice.domain.auth.dto;

import jakarta.validation.constraints.NotNull;

public record IdTokenRequest(@NotNull String idToken) {}
