package online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import online.partyrun.jwtmanager.JwtGenerator;
import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.NoSuchRefreshTokenException;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.AuthService;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Role;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import online.partyrun.partyrunauthenticationservice.domain.token.entity.RefreshToken;
import online.partyrun.partyrunauthenticationservice.domain.token.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FirebaseAuthService implements AuthService {
    MemberService memberService;
    FirebaseHandler firebaseHandler;
    JwtGenerator jwtGenerator;
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtToken authorize(String idToken) {
        final TokenResponse tokenResponse = firebaseHandler.verifyIdToken(idToken);
        final MemberResponse member =
                memberService.getMember(
                        new MemberRequest(tokenResponse.uid(), tokenResponse.name()));
        final Set<String> roles =
                member.roles().stream().map(Role::name).collect(Collectors.toSet());
        final JwtToken jwtToken = jwtGenerator.generate(member.id(), roles);
        final RefreshToken refreshToken = new RefreshToken(jwtToken.refreshToken(), member.id());
        refreshTokenRepository.save(refreshToken);
        return jwtToken;
    }

    @Override
    public JwtToken refreshAccessToken(String refreshToken) {
        if (!refreshTokenRepository.existsById(refreshToken)) {
            throw new NoSuchRefreshTokenException(refreshToken);
        }
        return jwtGenerator.refresh(refreshToken);
    }
}
