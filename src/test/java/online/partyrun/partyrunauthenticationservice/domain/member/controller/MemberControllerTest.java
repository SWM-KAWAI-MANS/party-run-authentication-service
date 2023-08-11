package online.partyrun.partyrunauthenticationservice.domain.member.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import online.partyrun.partyrunauthenticationservice.domain.auth.controller.ControllerTestConfig;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MembersResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MessageResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.MemberNotFoundException;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import online.partyrun.testmanager.docs.RestControllerTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

@AutoConfigureDataJpa
@Import(ControllerTestConfig.class)
@DisplayName("MemberController")
class MemberControllerTest extends RestControllerTest {

    @Autowired MemberService memberService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버를_조회할_때 {

        @Test
        @DisplayName("정상적인 멤버의 토큰이 주어지면 멤버 정보를 조회한다.")
        void successFindMember() throws Exception {
            MemberResponse response =
                    new MemberResponse(
                            "parkseongwoo",
                            "박성우",
                            "https://avatars.githubusercontent.com/u/134378498?s=400&u=72e57bdb2eafcad3d0c8b8e137349397eefce35f&v=4");
            given(memberService.findMember("defaultUser")).willReturn(response);

            ResultActions actions =
                    mockMvc.perform(
                            get("/members/me")
                                    .header(
                                            "Authorization",
                                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8));
            actions.andExpect(status().isOk()).andExpect(content().json(toRequestBody(response)));

            setPrintDocs(actions, "find member");
        }

        @Test
        @DisplayName("비정상적인 멤버의 토큰이 주어지면 Not Found를 반환한다.")
        void FailToFindMember() throws Exception {
            given(memberService.findMember("defaultUser")).willThrow(MemberNotFoundException.class);

            ResultActions actions =
                    mockMvc.perform(
                            get("/members/me")
                                    .header("Authorization", "invalidMembersToken")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8));
            actions.andExpect(status().isNotFound());

            setPrintDocs(actions, "fail to find member");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버_다수를_조회할_때 {

        @Test
        @DisplayName("정상적인 멤버의 토큰이 주어지면 멤버 정보를 조회한다.")
        void successFindMember() throws Exception {
            String id1 = "parkseongwoo";
            String id2 = "parkhyunjun";

            MembersResponse response =
                    new MembersResponse(
                            List.of(
                                    new MemberResponse(
                                            "parkseongwoo",
                                            "박성우",
                                            "https://avatars.githubusercontent.com/u/134378498?s=400&u=72e57bdb2eafcad3d0c8b8e137349397eefce35f&v=4"),
                                    new MemberResponse(
                                            "parkhyunjun",
                                            "박현준",
                                            "https://avatars.githubusercontent.com/u/134378498?s=400&u=72e57bdb2eafcad3d0c8b8e137349397eefce35f&v=4")));

            given(memberService.findMembers(List.of(id1, id2))).willReturn(response);

            ResultActions actions =
                    mockMvc.perform(
                            get("/members")
                                    .header(
                                            "Authorization",
                                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8)
                                    .param("ids", id1)
                                    .param("ids", id2));
            actions.andExpect(status().isOk()).andExpect(content().json(toRequestBody(response)));

            setPrintDocs(actions, "find members");
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버를_삭제할_때 {

        @Test
        @DisplayName("정상적으로 처리되었다는 메시지를 응답한다.")
        void successDeleteMember() throws Exception {
            final MessageResponse response = new MessageResponse();

            given(memberService.deleteMember("defaultUser")).willReturn(response);

            ResultActions actions =
                    mockMvc.perform(
                            delete("/members/me")
                                    .with(csrf())
                                    .header(
                                            "Authorization",
                                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .characterEncoding(StandardCharsets.UTF_8));
            actions.andExpect(status().isOk()).andExpect(content().json(toRequestBody(response)));

            setPrintDocs(actions, "find members");
        }
    }
}
