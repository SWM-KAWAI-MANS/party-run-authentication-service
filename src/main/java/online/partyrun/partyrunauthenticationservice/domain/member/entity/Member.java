package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {

    @Id
    String id;
    @Unique String authId;
    String name;
    Set<Role> roles = Set.of(Role.ROLE_USER);

    public Member(String authId, String name) {
        this.id = UUID.randomUUID().toString();
        this.authId = authId;
        this.name = name;
    }

}
