package online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.partyrun.jwtmanager.JwtExtractor;
import online.partyrun.jwtmanager.JwtGenerator;
import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.NoSuchRefreshTokenException;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.AuthService;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberAuthRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberAuthResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Role;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import online.partyrun.partyrunauthenticationservice.domain.auth.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FirebaseAuthService implements AuthService {
    MemberService memberService;
    FirebaseHandler firebaseHandler;
    JwtGenerator jwtGenerator;
    JwtExtractor jwtExtractor;
    RefreshTokenRepository refreshTokenRepository;


    @Override
    @Transactional
    public JwtToken authorize(String idToken) {
        final TokenResponse tokenResponse = firebaseHandler.verifyIdToken(idToken);
        final MemberAuthResponse member =
                memberService.getMember(
                        new MemberAuthRequest(tokenResponse.uid(), tokenResponse.name()));
        final String memberId = member.id();

        final Set<String> roles =
                member.roles().stream().map(Role::name).collect(Collectors.toSet());
        final JwtToken jwtToken = jwtGenerator.generate(memberId, roles);

        refreshTokenRepository.set(memberId, jwtToken.refreshToken());
        return jwtToken;
    }

    @Override
    @Transactional(readOnly = true)
    public JwtToken refreshAccessToken(String refreshToken) {
        final String memberId = jwtExtractor.extractRefreshToken(refreshToken).id();
        validateRefreshToken(memberId, refreshToken);

        final JwtToken jwtToken = jwtGenerator.refresh(refreshToken);

        refreshTokenRepository.set(memberId, jwtToken.refreshToken());
        return jwtToken;
    }

    private void validateRefreshToken(String memberId, String refreshToken) {
        if (!refreshTokenRepository.existsBy(memberId, refreshToken)) {
            throw new NoSuchRefreshTokenException(refreshToken);
        }
    }
}
