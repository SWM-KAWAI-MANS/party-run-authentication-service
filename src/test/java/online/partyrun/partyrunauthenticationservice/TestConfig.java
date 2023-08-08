package online.partyrun.partyrunauthenticationservice;


import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import software.amazon.awssdk.services.sns.SnsClient;

@TestConfiguration
public class TestConfig {

    @MockBean
    SnsClient snsClient;

    @MockBean
    FirebaseHandler firebaseHandler;
}
