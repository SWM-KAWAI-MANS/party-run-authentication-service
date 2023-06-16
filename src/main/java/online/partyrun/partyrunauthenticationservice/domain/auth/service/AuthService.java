package online.partyrun.partyrunauthenticationservice.domain.auth.service;

import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.AccessTokenResponse;

public interface AuthService {
    JwtToken authorize(String token);

    AccessTokenResponse refreshAccessToken(String refreshToken);
}
