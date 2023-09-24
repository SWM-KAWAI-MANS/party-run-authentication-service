package online.partyrun.partyrunauthenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PartyRunAuthenticationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PartyRunAuthenticationServiceApplication.class, args);
    }
}
