package online.partyrun.partyrunauthenticationservice.domain.member.service;

import online.partyrun.partyrunauthenticationservice.TestConfig;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberAuthRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberAuthResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberNameUpdateRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Role;
import online.partyrun.partyrunauthenticationservice.domain.member.event.Event;
import online.partyrun.partyrunauthenticationservice.domain.member.event.MemberEventPublisher;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.MemberNotFoundException;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
@Import(TestConfig.class)
@DisplayName("MemberService")
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    MemberEventPublisher memberEventPublisher;

    String authId = "authId";
    String name = "박현준";
    MemberAuthRequest memberAuthRequest = new MemberAuthRequest(authId, name);

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 이미_존재하는_멤버의_authId로_요청하면 {
        Member member = memberRepository.save(new Member("authId", "박현준"));

        @Test
        @DisplayName("멤버가 존재하는지 확인한다.")
        void getMember() {
            MemberAuthResponse response = memberService.getMember(memberAuthRequest);
            assertAll(
                    () ->
                            then(eventPublisher)
                                    .should(times(0))
                                    .publishEvent(Event.create(response.id())),
                    () -> assertThat(response.id()).isNotNull(),
                    () -> assertThat(response.authId()).isEqualTo(member.getAuthId()),
                    () -> assertThat(response.name()).isEqualTo(member.getName()),
                    () -> assertThat(response.roles()).isEqualTo(Set.of(Role.ROLE_USER)));
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 이미_존재하지않는_멤버의_authId로_요청하면 {

        @Test
        @DisplayName("새로운 멤버를 생성한다.")
        void getMember() {
            MemberAuthResponse response = memberService.getMember(memberAuthRequest);
            assertAll(
                    () ->
                            then(memberEventPublisher)
                                    .should(times(1))
                                    .publish(Event.create(response.id())),
                    () -> assertThat(response.id()).isNotNull(),
                    () -> assertThat(response.authId()).isEqualTo(memberAuthRequest.authId()),
                    () -> assertThat(response.name()).isEqualTo(memberAuthRequest.name()),
                    () -> assertThat(response.roles()).isEqualTo(Set.of(Role.ROLE_USER)));
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버를_삭제할_때 {

        @Test
        @DisplayName("DB에서 제거한다.")
        void deleteMember() {
            final Member savedMember = memberRepository.save(new Member("authId", "박성우"));

            assertThat(memberService.findMember(savedMember.getId())).isNotNull();

            memberService.deleteMember(savedMember.getId());
            Assertions.assertAll(
                    () ->
                            then(eventPublisher)
                                    .should(times(1))
                                    .publishEvent(Event.delete(savedMember.getId())),
                    () ->
                            then(memberEventPublisher)
                                    .should(times(1))
                                    .publish(Event.delete(savedMember.getId())),
                    () ->
                            assertThatThrownBy(() -> memberService.findMember(savedMember.getId()))
                                    .isInstanceOf(MemberNotFoundException.class));
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 멤버의_이름을_변경할_때 {

        @Test
        @DisplayName("멤버가 존재하지 않으면 예외를 던진다.")
        void throwException() {
            assertThatThrownBy(() -> memberService.updateName("invalid member id", new MemberNameUpdateRequest("new name")))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        @DisplayName("이름을 변경한다.")
        void updateName() {
            Member member = memberRepository.save(new Member(authId, name));

            final String newName = "박성우";
            memberService.updateName(member.getId(), new MemberNameUpdateRequest(newName));

            assertThat(memberRepository.findById(member.getId()).orElseThrow().getName()).isEqualTo(newName);
        }
    }
}
