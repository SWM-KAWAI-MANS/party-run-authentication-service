package online.partyrun.partyrunauthenticationservice.domain.member.entity;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("멤버의 이름을 변경할 수 있디.")
    public void updateName() {
        final Member member = new Member("authId", "박성우");
        final String newName = "노준혁";
        member.updateName(newName);

        assertThat(member.getName()).isEqualTo(newName);
    }
}