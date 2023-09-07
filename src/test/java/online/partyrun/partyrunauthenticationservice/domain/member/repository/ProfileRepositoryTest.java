package online.partyrun.partyrunauthenticationservice.domain.member.repository;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import online.partyrun.partyrunauthenticationservice.TestConfig;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidImageFileException;
import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import(TestConfig.class)
@SpringBootTest
@DisplayName("ProfileRepository")
class ProfileRepositoryTest {
    @Autowired
    ProfileRepository profileRepository;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class multipartFile이_잘못되었으면 {
        static Stream<Arguments> invalidMultipartArgs() {
            return Stream.of(
                    Arguments.of(new MockMultipartFile(null)),
                    Arguments.of(new MockMultipartFile("hello.txt")),
                    Arguments.of(new MockMultipartFile("hello"))
            );
        }

        @ParameterizedTest
        @MethodSource("invalidMultipartArgs")
        @DisplayName("MultipartFile이 잘못되었으면 예외 처리를 한다")
        void throwException(MultipartFile file) {
            assertThatThrownBy(() -> profileRepository.save(file))
                    .isInstanceOf(InvalidImageFileException.class);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 정상적인_파일확장자일_때 {

        static Stream<Arguments> multipartArgs() {
            return Stream.of(
                    Arguments.of(new MockMultipartFile("hello.png")),
                    Arguments.of(new MockMultipartFile("hello.jpeg")),
                    Arguments.of(new MockMultipartFile("hello.jpg"))
            );
        }

        @ParameterizedTest
        @MethodSource("multipartArgs")
        @DisplayName("에러를 반환하지 않는다")
        void notThrowException(MultipartFile file) {
            assertThatThrownBy(() -> profileRepository.save(file))
                    .isNotExactlyInstanceOf(InvalidImageFileException.class);
        }
    }
}

class MockMultipartFile implements MultipartFile {
    String name;

    public MockMultipartFile(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public void transferTo(File dest) throws IllegalStateException {

    }
}
