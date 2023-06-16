package online.partyrun.partyrunauthenticationservice.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberMapper;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;

import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {
    MemberRepository memberRepository;
    MemberMapper memberMapper;

    public MemberResponse getMember(MemberRequest request) {
        final Member member =
                memberRepository
                        .findByAuthId(request.authId())
                        .orElseGet(() -> memberRepository.save(memberMapper.toEntity(request)));
        return memberMapper.toResponse(member);
    }
}
