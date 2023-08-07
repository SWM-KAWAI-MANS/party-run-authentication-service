package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.event.MemberCreateEvent;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member extends AbstractAggregateRoot<Member> {

    @Id
    private String id;

    @Unique String authId;

    @Embedded Name name;

    Set<Role> roles = Set.of(Role.ROLE_USER);

    public Member(String authId, String name) {
        this.id = UUID.randomUUID().toString();
        this.authId = authId;
        this.name = new Name(name);

        registerEvent(new MemberCreateEvent(this.id));
    }

    public String getName() {
        return name.getValue();
    }
}
