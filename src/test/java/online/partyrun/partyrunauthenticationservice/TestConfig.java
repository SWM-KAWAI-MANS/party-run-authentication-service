package online.partyrun.partyrunauthenticationservice;

import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;
import online.partyrun.partyrunauthenticationservice.domain.member.event.MemberEventPublisher;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.GenericApplicationContext;

import software.amazon.awssdk.services.sns.SnsClient;

@TestConfiguration
public class TestConfig {

    @MockBean SnsClient snsClient;

    @MockBean FirebaseHandler firebaseHandler;

    @MockBean MemberEventPublisher memberEventPublisher;

    @Bean
    @Primary
    public GenericApplicationContext genericApplicationContext(
            final GenericApplicationContext gac) {
        return Mockito.spy(gac);
    }
}
