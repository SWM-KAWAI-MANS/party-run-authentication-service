package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidMemberNameException;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Name {

    private static final int MAX_LENGTH = 6;

    @Column(name = "name")
    String value;

    public Name(String value) {
        validateName(value);
        this.value = refine(value);
    }

    private void validateName(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new InvalidMemberNameException();
        }
    }

    private String refine(String value) {
        if (value.length() > MAX_LENGTH) {
            return value.substring(0, MAX_LENGTH);
        }
        return value;
    }
}
