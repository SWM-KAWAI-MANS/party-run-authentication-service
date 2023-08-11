package online.partyrun.partyrunauthenticationservice.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.dto.*;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.MemberNotFoundException;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {
    MemberRepository memberRepository;

    @Transactional
    public MemberAuthResponse getMember(MemberAuthRequest request) {
        final Member member =
                memberRepository
                        .findByAuthId(request.authId())
                        .orElseGet(() -> memberRepository.save(request.toEntity()));
        return new MemberAuthResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMember(String id) {
        final Member member =
                memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));

        return new MemberResponse(member);
    }

    public MembersResponse findMembers(List<String> ids) {
        final List<Member> members = memberRepository.findAllById(ids);

        return MembersResponse.from(members);
    }

    public MessageResponse deleteMember(String id) {
        memberRepository.deleteById(id);

        return new MessageResponse();
    }
}
