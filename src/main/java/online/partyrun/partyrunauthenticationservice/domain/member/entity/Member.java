package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    private static final String DEFAULT_PROFILE =
            "https://avatars.githubusercontent.com/u/134378498?s=400&u=72e57bdb2eafcad3d0c8b8e137349397eefce35f&v=4";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Unique String authId;

    @Embedded Name name;

    @Embedded Profile profile;

    Set<Role> roles = Set.of(Role.ROLE_USER);

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Member(String authId, String name) {
        this.authId = authId;
        this.name = new Name(name);
        this.profile = new Profile(DEFAULT_PROFILE);
    }

    public String getId() {
        return this.id.toString();
    }

    public String getName() {
        return name.getValue();
    }
}
