package online.partyrun.partyrunauthenticationservice.domain.auth.controller;

import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.config.docs.RestDocumentTest;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.IdTokenRequest;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@DisplayName("AuthController는")
class AuthControllerTest extends RestDocumentTest {
    @MockBean
    AuthService authService;
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 로그인_요청을 {
        IdTokenRequest request = new IdTokenRequest("idSample");
        final JwtToken expexted = new JwtToken("3Y2NiZmIwMDg5NzQ1ZDA1ZiIs.eyJpZCI6IjY0ODIiZmIwMDg5NzQ1ZD4NTc3Y2NiZmIwMDg5NzQ1ZDA1ZiIsImV4cCI6MTY4NzMwNzY1NH0.4Jka7hW3NWESLOOmNj-_XK-Yf9TKoxW42Vq_aMO6jB_uIp6-mpdamJ43F7ADu57RTkbCnMJ8b06TA5kLEIlJqQ",
                "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjY0ODI4NTc3Y2NiZmIwMDg5NzQ1ZDA1ZiIsImV4cCI6MTY4NzMwNzY1NH0.4Jka7hW3NWESLOOmNj-_XK-Yf9TKoxW42Vq_aMO6jB_uIp6-mpdamJ43F7ADu57RTkbCnMJ8b06TA5kLEIlJqQ");
        @Test
        @DisplayName("수행한다")
        void successLogin() throws Exception {
            when(authService.authorize(anyString())).thenReturn(expexted);
            ResultActions perform =
                    mockMvc.perform(
                            post("/auth")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8)
                                    .content(toRequestBody(request)));

            perform.andExpect(status().isCreated());

            setPrintDocs(perform, "success login");
        }
    }
}