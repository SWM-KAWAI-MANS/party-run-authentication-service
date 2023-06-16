package online.partyrun.partyrunauthenticationservice.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@DisplayName("MemberRepository")
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class Member를_authId로_조회시 {
        String authId = "authIdSample";
        Member member = memberRepository.save(new Member(authId, "name"));

        @Test
        @DisplayName("해당하는 Member를 반환한다")
        void returnMember() {
            final Member result = memberRepository.findByAuthId(authId).get();

            assertThat(result.getAuthId()).isEqualTo(member.getAuthId());
        }
    }
}
