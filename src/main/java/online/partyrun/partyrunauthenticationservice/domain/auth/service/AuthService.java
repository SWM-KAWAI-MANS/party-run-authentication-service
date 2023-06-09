package online.partyrun.partyrunauthenticationservice.domain.auth.service;

import online.partyrun.jwtmanager.dto.JwtToken;

public interface AuthService {
    JwtToken authorize(String token);
}
