package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidMemberNameException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Name")
class NameTest {

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 이름을_생성할_때 {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("빈 값이면 예외를 던진다.")
        void returnInvalidException(String value) {
            assertThatThrownBy(() -> new Name(value))
                    .isInstanceOf(InvalidMemberNameException.class);
        }

        @Test
        @DisplayName("6자 초과이면 6자까지만 저장한다.")
        void createSixLength() {
            String value = "박성우박현준노준혁";
            Name name = new Name(value);

            assertThat(name.getValue()).isEqualTo(value.substring(0, 6));
        }

        @Test
        @DisplayName("6자 미만이면 바로 저장한다.")
        void create() {
            String value = "박성우박현준";
            Name name = new Name(value);

            assertThat(name.getValue()).isEqualTo(value);
        }
    }
}