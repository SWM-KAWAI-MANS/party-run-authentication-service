package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidMemberProfileException;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {
    private static final String DEFAULT_PROFILE =
            "https://avatars.githubusercontent.com/u/134378498?s=400&u=72e57bdb2eafcad3d0c8b8e137349397eefce35f&v=4";

    @Column(name = "profile")
    String value = DEFAULT_PROFILE;

    public Profile(String value) {
        validateProfile(value);
        this.value = value;
    }

    private void validateProfile(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new InvalidMemberProfileException(value);
        }
    }
}
