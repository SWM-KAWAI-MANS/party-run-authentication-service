package online.partyrun.partyrunauthenticationservice.domain.auth.repository;

import online.partyrun.partyrunauthenticationservice.TestConfig;
import online.partyrun.testmanager.redis.EnableRedisTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableRedisTest
@Import(TestConfig.class)
@DisplayName("RefreshTokenRepository")
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expire-second}")
    private long refreshExpireSecond;
    String name = "박성우";
    String refreshToken = "refreshToken";

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class set은 {

        @Test
        @DisplayName("RefreshToken이 이미 저장되어있다면, .")
        void setRefreshToken() {

        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class existsBy는 {

        @Test
        @DisplayName("RefreshToken이 저장되었는지 확인한다.")
        void exists() {
            refreshTokenRepository.set(name, refreshToken);

            assertThat(refreshTokenRepository.existsBy(name, refreshToken)).isTrue();
        }

        @Test
        @DisplayName("시간 초과 이후 조회하면 false를 반환한다.")
        void notExistsByExpireTime() throws InterruptedException {
            refreshTokenRepository.set(name, refreshToken);
            Thread.sleep(refreshExpireSecond * 1000);

            assertThat(refreshTokenRepository.existsBy(name, refreshToken)).isFalse();
        }

        @ParameterizedTest
        @CsvSource(value = {"박성우,invalidRefreshToken", "parkseongwoo,refreshToken"})
        @DisplayName("잘못된 key 혹은 값으로 조회하면 false를 반환한다.")
        void notExistsByInvalidKeyOrValue(String key, String value) {
            refreshTokenRepository.set(name, refreshToken);

            assertThat(refreshTokenRepository.existsBy(key, value)).isFalse();
        }
    }
}