package online.partyrun.partyrunauthenticationservice.domain.auth.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import online.partyrun.jwtmanager.JwtGenerator;
import online.partyrun.jwtmanager.dto.JwtToken;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.service.MemberService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FirebaseAuthService implements AuthService {
    MemberService memberService;
    FirebaseAuth firebaseAuth;
    JwtGenerator jwtGenerator;

    @Override
    public JwtToken authorize(String idToken) {
            FirebaseToken firebaseToken = getFirebaseToken(idToken);
            MemberResponse member = memberService.getMember(new MemberRequest(firebaseToken.getUid(), firebaseToken.getName()));
            return jwtGenerator.generate(member.id());
    }

    private FirebaseToken getFirebaseToken(String idToken) {
        try {
            return firebaseAuth.verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
