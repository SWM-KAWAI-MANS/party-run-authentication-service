package online.partyrun.partyrunauthenticationservice.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import online.partyrun.jwtmanager.JwtGenerator;
import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.config.RedisTestConfig;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.AccessTokenResponse;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.IllegalIdTokenException;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.NoSuchRefreshTokenException;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseAuthService;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.TokenResponse;
import online.partyrun.partyrunauthenticationservice.domain.token.entity.RefreshToken;
import online.partyrun.partyrunauthenticationservice.domain.token.repository.RefreshTokenRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest
@DisplayName("FirebaseAuthService")
@Import(RedisTestConfig.class)
class FirebaseAuthServiceTest {
    @MockBean FirebaseHandler firebaseHandler;

    @Autowired FirebaseAuthService firebaseAuthService;

    @Autowired JwtGenerator jwtGenerator;

    @Autowired RefreshTokenRepository refreshTokenRepository;

    String idToken = "idToken";
    String name = "박현준";
    String jwtTokenRegex = "^([A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*)?$";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class idTokne이_주어지면 {

        @Test
        @DisplayName("jwtToken을 반환한다.")
        void returnJwtToken() {
            given(firebaseHandler.verifyIdToken(idToken))
                    .willReturn(new TokenResponse(idToken, name));
            JwtToken jwtToken = firebaseAuthService.authorize(idToken);
            assertAll(
                    () -> assertThat(jwtToken.accessToken()).matches(jwtTokenRegex),
                    () -> assertThat(jwtToken.refreshToken()).matches(jwtTokenRegex));
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 잘못된_idTokne이_주어지면 {
        String invalidIdToken = "invalidIdToken";

        @Test
        @DisplayName("예외를 던진다.")
        void returnJwtToken() {
            given(firebaseHandler.verifyIdToken(invalidIdToken))
                    .willThrow(IllegalIdTokenException.class);
            assertThatThrownBy(() -> firebaseAuthService.authorize(invalidIdToken))
                    .isInstanceOf(IllegalIdTokenException.class);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class refreshToken이_주어지면 {

        String refreshToken = jwtGenerator.generate(idToken).refreshToken();

        @Test
        @DisplayName("accessToken을 반환한다.")
        void returnAccessToken() {
            refreshTokenRepository.save(new RefreshToken(refreshToken, name));

            AccessTokenResponse result = firebaseAuthService.refreshAccessToken(refreshToken);

            assertThat(result.accessToken()).matches(jwtTokenRegex);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 존재하지_않는_refreshToken이_주어지면 {

        String invalidRefreshToken = "invalidRefreshToken";

        @Test
        @DisplayName("예외를 던진다")
        void throwExceptions() {
            assertThatThrownBy(() -> firebaseAuthService.refreshAccessToken(invalidRefreshToken))
                    .isInstanceOf(NoSuchRefreshTokenException.class);
        }
    }
}
