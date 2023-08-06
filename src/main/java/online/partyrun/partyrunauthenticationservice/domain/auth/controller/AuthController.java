package online.partyrun.partyrunauthenticationservice.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.IdTokenRequest;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken login(@RequestBody @Valid IdTokenRequest request) {
        return authService.authorize(request.idToken());
    }

    @PostMapping("access")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
        return authService.refreshAccessToken(refreshToken);
    }
}
