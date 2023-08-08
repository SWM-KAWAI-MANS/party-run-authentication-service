package online.partyrun.partyrunauthenticationservice.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberAuthRequest;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberAuthResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.dto.MemberResponse;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.MemberNotFoundException;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;

import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {
    MemberRepository memberRepository;

    public MemberAuthResponse getMember(MemberAuthRequest request) {
        final Member member =
                memberRepository
                        .findByAuthId(request.authId())
                        .orElseGet(() -> memberRepository.save(request.toEntity()));
        return new MemberAuthResponse(member);
    }

    public MemberResponse findMember(String id) {
        final Member member =
                memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
        return new MemberResponse(member);
    }
}
