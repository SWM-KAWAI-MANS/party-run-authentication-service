package online.partyrun.partyrunauthenticationservice.domain.member;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController {

    @GetMapping("member")
    public String member(Authentication authentication) {
        log.info("{}", authentication.getName());
        return authentication.getName();
    }
}
