package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Unique String authId;

    @Embedded Name name;

    Set<Role> roles = Set.of(Role.ROLE_USER);

    public Member(String authId, String name) {
        this.authId = authId;
        this.name = new Name(name);
    }

    public String getId() {
        return this.id.toString();
    }

    public String getName() {
        return name.getValue();
    }
}
