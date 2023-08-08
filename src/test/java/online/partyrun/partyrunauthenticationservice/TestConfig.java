package online.partyrun.partyrunauthenticationservice;

import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;
import online.partyrun.partyrunauthenticationservice.domain.member.event.MemberEventPublisher;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {

    @MockBean MemberEventPublisher memberEventPublisher;

    @MockBean FirebaseHandler firebaseHandler;
}
