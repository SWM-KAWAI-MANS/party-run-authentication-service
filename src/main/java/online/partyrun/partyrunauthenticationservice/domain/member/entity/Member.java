package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {
    @Id String id;
    @Unique String authId;
    String name;
    Set<Role> roles = Set.of(Role.ROLE_USER);

    public Member(String authId, String name) {
        this.authId = authId;
        this.name = name;
    }
}
