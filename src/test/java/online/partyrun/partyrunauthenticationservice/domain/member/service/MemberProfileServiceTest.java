package online.partyrun.partyrunauthenticationservice.domain.member.service;

import online.partyrun.partyrunauthenticationservice.TestConfig;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.InvalidMultipartImageException;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.ProfileRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestConfig.class)
@DisplayName("MemberProfileService")
class MemberProfileServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberProfileService memberProfileService;
    @MockBean
    ProfileRepository profileRepository;
    @Autowired
    MemberRepository memberRepository;
    String authId = "authId";
    String name = "박현준";

    @Test
    @DisplayName("profile 사진을 변경한다")
    void updateProfile() {
        Member member = memberRepository.save(new Member(authId, name));
        final MockMultipartFile profileImage = new CustomMockMultipartFile("profile.png");
        final String before = memberService.findMember(member.getId()).profile();
        memberProfileService.updateProfile(member.getId(), profileImage);

        assertThat(memberService.findMember(member.getId()).profile()).isNotEqualTo(before);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class multipartFile이_잘못되었으면 {
        static Stream<Arguments> invalidMultipartArgs() {
            return Stream.of(
                    Arguments.of(new CustomMockMultipartFile("hello.txt")),
                    Arguments.of(new CustomMockMultipartFile("hello"))
            );
        }

        @ParameterizedTest
        @MethodSource("invalidMultipartArgs")
        @DisplayName("MultipartFile이 잘못되었으면 예외 처리를 한다")
        void throwException(MultipartFile file) {
            assertThatThrownBy(() -> memberProfileService.updateProfile(authId, file))
                    .isInstanceOf(InvalidMultipartImageException.class);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 정상적인_파일확장자일_때 {

        static Stream<Arguments> multipartArgs() {
            return Stream.of(
                    Arguments.of(new CustomMockMultipartFile("hello.jpeg")),
                    Arguments.of(new CustomMockMultipartFile("hello.jpg")),
                    Arguments.of(new CustomMockMultipartFile("hello.png"))
            );
        }

        @ParameterizedTest
        @MethodSource("multipartArgs")
        @DisplayName("에러를 반환하지 않는다")
        void notThrowException(MultipartFile file) {
            assertThatThrownBy(() ->  memberProfileService.updateProfile(authId, file))
                    .isNotExactlyInstanceOf(InvalidMultipartImageException.class);
        }
    }

}


class CustomMockMultipartFile extends MockMultipartFile {
    public CustomMockMultipartFile(String name) {
        super(name, new byte[1]);
    }

    @Override
    public String getOriginalFilename() {
        return this.getName();
    }
}

