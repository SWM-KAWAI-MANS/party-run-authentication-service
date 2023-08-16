package online.partyrun.partyrunauthenticationservice.domain.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import online.partyrun.partyrunauthenticationservice.domain.member.dto.*;
import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;
import online.partyrun.partyrunauthenticationservice.domain.member.event.Event;
import online.partyrun.partyrunauthenticationservice.domain.member.exception.MemberNotFoundException;
import online.partyrun.partyrunauthenticationservice.domain.member.repository.MemberRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MemberService {
    MemberRepository memberRepository;
    ApplicationEventPublisher eventPublisher;

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
        final Member member = getMember(id);

        return new MemberResponse(member);
    }

    private Member getMember(String id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
    }

    public MembersResponse findMembers(List<String> ids) {
        final List<Member> members = memberRepository.findAllById(ids);

        return MembersResponse.from(members);
    }

    @Transactional
    public MessageResponse deleteMember(String id) {
        final Member member = getMember(id);
        memberRepository.delete(member);

        eventPublisher.publishEvent(Event.delete(id));

        return new MessageResponse();
    }
}
