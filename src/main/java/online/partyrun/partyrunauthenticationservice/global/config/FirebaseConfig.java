package online.partyrun.partyrunauthenticationservice.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import online.partyrun.partyrunauthenticationservice.domain.auth.service.firebase.FirebaseHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.secret}")
    private String secret;

    @Bean
    public FirebaseHandler firebaseHandler() throws IOException {
        return new FirebaseHandler(FirebaseAuth.getInstance(firebaseApp()));
    }

    private FirebaseApp firebaseApp() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(secret.getBytes());
        FirebaseOptions options =
                FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(inputStream))
                        .build();
        return FirebaseApp.initializeApp(options);
    }
}