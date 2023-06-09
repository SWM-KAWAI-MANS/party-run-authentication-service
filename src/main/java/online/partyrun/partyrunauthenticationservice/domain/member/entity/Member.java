package online.partyrun.partyrunauthenticationservice.domain.member.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {
    @Id
    String id;
    String authId;
    String name;

    public Member(String authId, String name) {
        this.authId = authId;
        this.name = name;
    }
}
