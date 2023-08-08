package online.partyrun.partyrunauthenticationservice.domain.member.controller;

import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.MemberNotFoundException;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import online.partyrun.testmanager.docs.RestControllerTest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataJpa
@WebMvcTest(MemberController.class)
@DisplayName("MemberController")
class MemberControllerTest extends RestControllerTest {

    @MockBean
    MemberService memberService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버를_조회할_때 {

        @Test
        @DisplayName("정상적인 멤버의 토큰이 주어지면 멤버 정보를 조회한다.")
        void successFindMember() throws Exception {
            MemberResponse response = new MemberResponse("parkseongwoo", "박성우", "https://avatars.githubusercontent.com/u/134378498?s=400&u=72e57bdb2eafcad3d0c8b8e137349397eefce35f&v=4");
            given(memberService.findMember(anyString())).willReturn(response);

            ResultActions actions =
                    mockMvc.perform(
                            get("/members/me")
                                    .header(
                                            "Authorization",
                                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8));
            actions.andExpect(status().isOk())
                    .andExpect(content().json(toRequestBody(response)));

            setPrintDocs(actions, "find member");
        }

        @Test
        @DisplayName("비정상적인 멤버의 토큰이 주어지면 Not Found를 반환한다.")
        void FailToFindMember() throws Exception {
            given(memberService.findMember(anyString())).willThrow(MemberNotFoundException.class);

            ResultActions actions =
                    mockMvc.perform(
                            get("/members/me")
                                    .header(
                                            "Authorization",
                                            "invalidMembersToken")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8));
            actions.andExpect(status().isNotFound());

            setPrintDocs(actions, "fail to find member");
        }
    }
}