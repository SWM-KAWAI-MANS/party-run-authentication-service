package online.partyrun.partyrunauthenticationservice.domain.token.entity;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import online.partyrun.partyrunauthenticationservice.domain.token.exception.EmptyStringException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("RefreshToken은")
class RefreshTokenTest {
    String value = "RefreshToken";
    String memberId = "memberId";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class value와_memberId를_받으면 {

        @Test
        @DisplayName("RefreshToken 엔티티를 생성한다.")
        void createRefreshToken() {
            assertThatCode(() -> new RefreshToken(value, memberId)).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 잘못된_value를_받으면 {

        @ParameterizedTest
        @NullSource
        @EmptySource
        @DisplayName("예외를 반환한다.")
        void throwException(String invalidValue) {
            assertThatThrownBy(() -> new RefreshToken(invalidValue, memberId))
                    .isInstanceOf(EmptyStringException.class);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 잘못된_memberId를_받으면 {

        @ParameterizedTest
        @NullSource
        @EmptySource
        @DisplayName("예외를 반환한다.")
        void throwException(String memberId) {
            assertThatThrownBy(() -> new RefreshToken(value, memberId))
                    .isInstanceOf(EmptyStringException.class);
        }
    }
}
