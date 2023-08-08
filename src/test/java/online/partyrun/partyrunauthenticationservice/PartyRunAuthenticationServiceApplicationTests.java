package online.partyrun.partyrunauthenticationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestConfig.class)
class PartyRunAuthenticationServiceApplicationTests {

    @Test
    void contextLoads() {}
}
