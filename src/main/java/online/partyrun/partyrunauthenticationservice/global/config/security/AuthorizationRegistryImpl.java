package online.partyrun.partyrunauthenticationservice.global.config.security;

import online.partyrun.springsecurityauthorizationmanager.AuthorizationRegistry;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationRegistryImpl implements AuthorizationRegistry {
    @Override
    public AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
            match(
                    final AuthorizeHttpRequestsConfigurer<HttpSecurity>
                                    .AuthorizationManagerRequestMatcherRegistry
                            r) {
        return r.anyRequest().permitAll();
    }
}
