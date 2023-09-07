package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.event.Event;

import org.checkerframework.checker.units.qual.N;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@EntityListeners(AuditingEntityListener.class)
public class Member extends AbstractAggregateRoot<Member> {

    @Id private String id;

    @Unique String authId;

    @Embedded Name name;

    @Embedded Profile profile;

    Set<Role> roles = Set.of(Role.ROLE_USER);

    boolean isDeleted;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Member(String authId, String name) {
        this.id = UUID.randomUUID().toString();
        this.authId = authId;
        this.name = new Name(name);
        this.profile = new Profile();

        registerEvent(Event.create(this.id));
    }

    public void updateName(String name) {
        this.name = new Name(name);
    }

    public String getName() {
        return this.name.getValue();
    }

    public String getProfile() {
        return this.profile.getValue();
    }

    public void updateProfile(Profile profile) {
        this.profile = profile;
    }
}
