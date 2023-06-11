package online.partyrun.partyrunauthenticationservice.domain.auth.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import online.partyrun.jwtmanager.JwtGenerator;
import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.AccessTokenResponse;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.IllegalIdTokenException;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.NoSuchRefreshTokenException;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import online.partyrun.partyrunauthenticationservice.domain.token.entity.RefreshToken;
import online.partyrun.partyrunauthenticationservice.domain.token.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FirebaseAuthService implements AuthService {
    MemberService memberService;
    FirebaseAuth firebaseAuth;
    JwtGenerator jwtGenerator;
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtToken authorize(String idToken) {
        final FirebaseToken firebaseToken = getFirebaseToken(idToken);
        final MemberResponse member = memberService.getMember(new MemberRequest(firebaseToken.getUid(), firebaseToken.getName()));
        final JwtToken jwtToken = jwtGenerator.generate(member.id());
        final RefreshToken refreshToken = new RefreshToken(jwtToken.refreshToken(), member.id());
        refreshTokenRepository.save(refreshToken);
        return jwtToken;
    }

    private FirebaseToken getFirebaseToken(String idToken) {
        try {
            return firebaseAuth.verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new IllegalIdTokenException(e.getMessage());
        }
    }

    @Override
    public AccessTokenResponse refreshAccessToken(String refreshToken) {
        if(!refreshTokenRepository.existsById(refreshToken)) {
            throw new NoSuchRefreshTokenException(refreshToken);
        }
        return new AccessTokenResponse(jwtGenerator.generateAccessToken(refreshToken));
    }

}
