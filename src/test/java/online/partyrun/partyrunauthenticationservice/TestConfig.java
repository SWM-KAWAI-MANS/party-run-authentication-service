package online.partyrun.partyrunauthenticationservice;

import io.awspring.cloud.s3.S3Template;
import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;
import online.partyrun.partyrunauthenticationservice.domain.member.event.MemberEventPublisher;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.GenericApplicationContext;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.sns.SnsClient;

@TestConfiguration
public class TestConfig {

    @MockBean SnsClient snsClient;

    @MockBean S3Client s3Client;
    @MockBean S3Template s3Template;
    @MockBean S3Presigner s3Presigner;

    @MockBean FirebaseHandler firebaseHandler;

    @MockBean MemberEventPublisher memberEventPublisher;

    @Bean
    @Primary
    public GenericApplicationContext genericApplicationContext(
            final GenericApplicationContext gac) {
        return Mockito.spy(gac);
    }
}
