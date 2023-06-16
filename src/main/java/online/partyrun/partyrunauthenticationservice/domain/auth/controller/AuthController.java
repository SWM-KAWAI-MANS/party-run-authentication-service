package online.partyrun.partyrunauthenticationservice.domain.auth.controller;

import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.AccessTokenResponse;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.IdTokenRequest;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping
    public ResponseEntity<JwtToken> login(@RequestBody @Valid IdTokenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.authorize(request.idToken()));
    }

    @PostMapping("access")
    public ResponseEntity<AccessTokenResponse> refreshAccessToken(
            @RequestHeader("Refresh-Token") String refreshToken) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.refreshAccessToken(refreshToken));
    }
}
