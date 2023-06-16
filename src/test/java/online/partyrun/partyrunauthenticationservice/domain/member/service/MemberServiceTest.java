package online.partyrun.partyrunauthenticationservice.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
@DisplayName("MemberService")
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MongoTemplate mongoTemplate;

    @Autowired MemberRepository memberRepository;

    String authId = "authId";
    String name = "박현준";
    MemberRequest memberRequest = new MemberRequest(authId, name);

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 이미_존재하는_멤버의_authId로_요청하면 {
        Member member = memberRepository.save(new Member("authId", "박현준"));

        @Test
        @DisplayName("멤버가 존재하는지 확인한다.")
        void getMember() {
            MemberResponse response = memberService.getMember(memberRequest);
            assertAll(
                    () -> assertThat(response.id()).isNotNull(),
                    () -> assertThat(response.authId()).isEqualTo(member.getAuthId()),
                    () -> assertThat(response.name()).isEqualTo(member.getName()));
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 이미_존재하지않는_멤버의_authId로_요청하면 {

        @Test
        @DisplayName("새로운 멤버를 생성한다.")
        void getMember() {
            MemberResponse response = memberService.getMember(memberRequest);
            assertAll(
                    () -> assertThat(response.id()).isNotNull(),
                    () -> assertThat(response.authId()).isEqualTo(memberRequest.authId()),
                    () -> assertThat(response.name()).isEqualTo(memberRequest.name()));
        }
    }
}
