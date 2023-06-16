package online.partyrun.partyrunauthenticationservice.domain.auth.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.config.docs.RestDocumentTest;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.AccessTokenResponse;
import online.partyrun.partyrunauthenticationservice.domain.auth.dto.IdTokenRequest;
import online.partyrun.partyrunauthenticationservice.domain.auth.exception.IllegalIdTokenException;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.AuthService;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

@DisplayName("AuthController는")
@WebMvcTest(AuthController.class)
class AuthControllerTest extends RestDocumentTest {
    @MockBean AuthService authService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 정상적인_idToken이_주어졌을_때 {
        IdTokenRequest request = new IdTokenRequest("idSample");
        JwtToken expected =
                new JwtToken(
                        "3Y2NiZmIwMDg5NzQ1ZDA1ZiIs.eyJpZCI6IjY0ODIiZmIwMDg5NzQ1ZD4NTc3Y2NiZmIwMDg5NzQ1ZDA1ZiIsImV4cCI6MTY4NzMwNzY1NH0.4Jka7hW3NWESLOOmNj-_XK-Yf9TKoxW42Vq_aMO6jB_uIp6-mpdamJ43F7ADu57RTkbCnMJ8b06TA5kLEIlJqQ",
                        "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjY0ODI4NTc3Y2NiZmIwMDg5NzQ1ZDA1ZiIsImV4cCI6MTY4NzMwNzY1NH0.4Jka7hW3NWESLOOmNj-_XK-Yf9TKoxW42Vq_aMO6jB_uIp6-mpdamJ43F7ADu57RTkbCnMJ8b06TA5kLEIlJqQ");

        @Test
        @DisplayName("토큰 발급을 수행한다.")
        void successGetToken() throws Exception {
            given(authService.authorize(anyString())).willReturn(expected);

            ResultActions actions =
                    mockMvc.perform(
                            post("/auth")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8)
                                    .content(toRequestBody(request)));
            actions.andExpect(status().isCreated());

            setPrintDocs(actions, "success login");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class idToken이_null이면 {
        IdTokenRequest nullRequest = new IdTokenRequest(null);

        @Test
        @DisplayName("BadRequest를 응답한다.")
        void returnException() throws Exception {
            ResultActions actions =
                    mockMvc.perform(
                            post("/auth")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8)
                                    .content(toRequestBody(nullRequest)));
            actions.andExpect(status().isBadRequest());

            setPrintDocs(actions, "idToken is null");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class idToken이_잘못된_형식이라면 {

        IdTokenRequest invalidIdTokenRequest = new IdTokenRequest("invalidIdToken");

        @Test
        @DisplayName("BadRequest를 응답한다.")
        void returnException() throws Exception {
            given(authService.authorize(invalidIdTokenRequest.idToken()))
                    .willThrow(new IllegalIdTokenException(""));

            ResultActions actions =
                    mockMvc.perform(
                            post("/auth")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8)
                                    .content(toRequestBody(invalidIdTokenRequest)));

            actions.andExpect(status().isBadRequest());

            setPrintDocs(actions, "idToken is invalid");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 정상적인_refreshToken이_주어졌을_떄 {
        String refreshToken =
                "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjY0ODI4NTc3Y2NiZmIwMDg5NzQ1ZDA1ZiIsImV4cCI6MTY4NzMwNzY1NH0.4Jka7hW3NWESLOOmNj-_XK-Yf9TKoxW42Vq_aMO6jB_uIp6-mpdamJ43F7ADu57RTkbCnMJ8b06TA5kLEIlJqQ";
        AccessTokenResponse expected =
                new AccessTokenResponse(
                        "Y2NiZmIwMDg5IUzUxMiJ9.eyJpZCI6IjY0ODI4NTc3Y2NiZmIwMDg5NzQ1ZDA1ZiIsImV4cCI6MTY4NzMwNzY1NH02Vq_aMO6jB_uIp6-mpda8b06TA5kLEIlJqQ");

        @Test
        @DisplayName("access token을 재발급해준다")
        void returnAccessToken() throws Exception {
            given(authService.refreshAccessToken(refreshToken)).willReturn(expected);

            ResultActions actions =
                    mockMvc.perform(
                            post("/auth/access")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8)
                                    .header("Refresh-Token", refreshToken));

            actions.andExpect(status().isCreated());

            setPrintDocs(actions, "refresh token");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class refreshToken이_body에_없으면 {

        @Test
        @DisplayName("BadRequest를 응답한다.")
        void returnException() throws Exception {
            ResultActions actions =
                    mockMvc.perform(
                            post("/auth/access")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8));

            actions.andExpect(status().isBadRequest());

            setPrintDocs(actions, "refresh token is null");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class refreshToken이_잘못된_형식이라면 {

        String refreshToken = "invalidRefreshToken";

        @Test
        @DisplayName("BadRequest를 응답한다.")
        void returnException() throws Exception {
            given(authService.refreshAccessToken(refreshToken))
                    .willThrow(new IllegalIdTokenException("idToken"));
            ResultActions actions =
                    mockMvc.perform(
                            post("/auth/access")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8)
                                    .header("Refresh-Token", refreshToken));

            actions.andExpect(status().isBadRequest());

            setPrintDocs(actions, "refresh token is invalid");
        }
    }
}
