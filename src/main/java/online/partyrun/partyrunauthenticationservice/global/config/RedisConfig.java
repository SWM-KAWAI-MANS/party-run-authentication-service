package online.partyrun.partyrunauthenticationservice.global.config;

import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    @Profile("prd")
    public LettuceConnectionFactory redisConnectionFactory(@Value("${spring.data.redis.cluster.nodes}") List<String> nodes) {
        RedisClusterConfiguration config = new RedisClusterConfiguration(nodes);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    @Profile("!prd")
    public LettuceConnectionFactory standaloneConnectionFactory(@Value("${spring.data.redis.url}") String redisUrl) {
        RedisURI redisURI = RedisURI.create(redisUrl);
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisURI.getHost(), redisURI.getPort()));
    }
}
