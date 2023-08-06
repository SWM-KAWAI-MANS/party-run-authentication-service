package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidMemberProfileException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Profile")
class ProfileTest {
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 프로필을_생성할_때 {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("빈 값이면 예외를 던진다.")
        void throwInvalidException(String value) {
            assertThatThrownBy(() -> new Profile(value))
                    .isInstanceOf(InvalidMemberProfileException.class);
        }

        @Test
        @DisplayName("https:// 로 시작하지 않으면 예외를 던진다.")
        void throwInvalidPrefixException() {
            String value = "www.naver.com";

            assertThatThrownBy(() -> new Profile(value))
                    .isInstanceOf(InvalidMemberProfileException.class);
        }
    }
}