package online.partyrun.partyrunauthenticationservice.domain.auth.service;

import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.config.RedisTestConfig;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.IllegalIdTokenException;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseAuthService;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.TokenResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("FirebaseAuthService")
@Import(RedisTestConfig.class)
class FirebaseAuthServiceTest {
    @MockBean
    FirebaseHandler firebaseHandler;

    @Autowired
    FirebaseAuthService firebaseAuthService;
    String idToken = "idToken";
    String name = "박현준";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class idTokne이_주어지면 {

        @Test
        @DisplayName("jwtToken을 반환한다.")
        void returnJwtToken() {
            given(firebaseHandler.verifyIdToken(idToken)).willReturn(new TokenResponse(idToken, name));
            String jwtTokenRegex = "^([A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*)?$";

            JwtToken jwtToken = firebaseAuthService.authorize(idToken);
            assertAll(
                    () -> assertThat(jwtToken.accessToken()).matches(jwtTokenRegex),
                    () -> assertThat(jwtToken.refreshToken()).matches(jwtTokenRegex)
            );
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 잘못된_idTokne이_주어지면 {
        String invalidIdToken = "invalidIdToken";

        @Test
        @DisplayName("예외를 던진다.")
        void returnJwtToken() {
            given(firebaseHandler.verifyIdToken(invalidIdToken)).willThrow(IllegalIdTokenException.class);
            assertThatThrownBy(() -> firebaseAuthService.authorize(invalidIdToken))
                    .isInstanceOf(IllegalIdTokenException.class);
        }
    }
}
