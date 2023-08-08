package online.partyrun.partyrunauthenticationservice.domain.auth.controller;

import online.partyrun.partyrunauthenticationservice.domain.auth.service.AuthService;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class ControllerTestConfig {

    @MockBean
    AuthService authService;

    @MockBean
    MemberService memberService;
}
