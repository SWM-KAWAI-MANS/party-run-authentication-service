package online.partyrun.partyrunauthenticationservice.domain.token.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refresh")
public class RefreshToken {
    @Id
    private String value;
    private String memberId;

    public RefreshToken(String value, String memberId) {
        this.value = value;
        this.memberId = memberId;
    }
}