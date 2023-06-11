package online.partyrun.partyrunauthenticationservice.domain.auth.service;

import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseAuthService;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.TokenResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("FirebaseAuthService")
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
            Assertions.assertThat(firebaseAuthService.authorize(idToken)).isNotNull();
        }
    }
}
