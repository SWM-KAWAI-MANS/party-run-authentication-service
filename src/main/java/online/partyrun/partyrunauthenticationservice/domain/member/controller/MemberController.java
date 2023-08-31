package online.partyrun.partyrunauthenticationservice.domain.member.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberNameUpdateRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MembersResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MessageResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public MembersResponse findMembers(@RequestParam List<String> ids) {
        return memberService.findMembers(ids);
    }

    @DeleteMapping("me")
    public MessageResponse deleteMember(Authentication auth) {
        return memberService.deleteMember(auth.getName());
    }

    @PatchMapping("name")
    public void updateName(Authentication auth, @Valid MemberNameUpdateRequest request) {
        memberService.updateName(auth.getName(), request);
    }
}
