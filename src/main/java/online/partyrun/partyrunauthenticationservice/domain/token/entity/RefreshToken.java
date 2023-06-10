package online.partyrun.partyrunauthenticationservice.domain.token.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.util.StringUtils;

@Getter
@RedisHash(value = "refresh")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshToken {
    @Id
    String value;
    String memberId;

    public RefreshToken(String value, String memberId) {
        validateEmpty(value);
        validateEmpty(memberId);
        this.value = value;
        this.memberId = memberId;
    }

    private void validateEmpty(String str) {
        if (!StringUtils.hasText(str)) {
            throw new IllegalArgumentException();
        }
    }
}
