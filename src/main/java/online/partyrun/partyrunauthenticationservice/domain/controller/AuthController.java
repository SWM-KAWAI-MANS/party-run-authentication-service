package online.partyrun.partyrunauthenticationservice.domain.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    FirebaseAuth firebaseAuth;

    @PostMapping
    public String login(@RequestHeader("Authorization") String authorization) throws FirebaseAuthException {
       log.info("{}", authorization);
       
        FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(authorization);
        return firebaseToken.getName();
    }
}
