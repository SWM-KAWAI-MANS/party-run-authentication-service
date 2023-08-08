package online.partyrun.partyrunauthenticationservice.domain.member.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("members")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {

    MemberService memberService;

    @GetMapping("me")
    public MemberResponse findMe(Authentication auth) {
        return memberService.findMember(auth.getName());
    }
}
