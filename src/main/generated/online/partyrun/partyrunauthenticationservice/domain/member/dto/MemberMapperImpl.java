package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import javax.annotation.processing.Generated;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-09T10:31:08+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.5 (Homebrew)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member toEntity(MemberRequest request) {
        if ( request == null ) {
            return null;
        }

        String authId = null;
        String name = null;

        authId = request.authId();
        name = request.name();

        Member member = new Member( authId, name );

        return member;
    }

    @Override
    public MemberResponse toResponse(Member entity) {
        if ( entity == null ) {
            return null;
        }

        String id = null;
        String name = null;

        id = entity.getId();
        name = entity.getName();

        MemberResponse memberResponse = new MemberResponse( id, name );

        return memberResponse;
    }
}
