package online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.auth.exception.IllegalIdTokenException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FirebaseHandler {

    FirebaseAuth firebaseAuth;

    public TokenResponse verifyIdToken(String idToken) {
        try {
            final FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(idToken);
            return new TokenResponse(firebaseToken.getUid(), firebaseToken.getName());
        } catch (FirebaseAuthException e) {
            throw new IllegalIdTokenException(e.getMessage());
        }
    }
}
