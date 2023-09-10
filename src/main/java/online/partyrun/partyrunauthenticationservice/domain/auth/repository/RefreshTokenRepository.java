package online.partyrun.partyrunauthenticationservice.domain.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.refresh-expire-second}")
    private long refreshExpireSecond;

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value, refreshExpireSecond, TimeUnit.SECONDS);
    }

    public boolean existsBy(String memberId, String refreshToken) {
        final String oldRefreshToken = redisTemplate.opsForValue().get(memberId);
        return Objects.nonNull(oldRefreshToken) && oldRefreshToken.equals(refreshToken);
    }
}
