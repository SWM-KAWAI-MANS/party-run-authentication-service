package online.partyrun.partyrunauthenticationservice.domain.member.dto;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {
    Member toEntity(MemberRequest request);

    MemberResponse toResponse(Member entity);
}
