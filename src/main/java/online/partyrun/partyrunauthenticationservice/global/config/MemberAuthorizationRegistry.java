package online.partyrun.partyrunauthenticationservice.global.config;

import online.partyrun.springsecurityauthorizationmanager.AuthorizationRegistry;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MemberAuthorizationRegistry implements AuthorizationRegistry {

    @Override
    public AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
    match(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>
                    .AuthorizationManagerRequestMatcherRegistry
                    r) {
        return r.requestMatchers(HttpMethod.POST, "/auth")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/access")
                .permitAll()
                .anyRequest()
                .hasRole("USER");
    }
}