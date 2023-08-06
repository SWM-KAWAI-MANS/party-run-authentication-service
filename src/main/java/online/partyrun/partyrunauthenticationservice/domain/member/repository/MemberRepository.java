package online.partyrun.partyrunauthenticationservice.domain.member.repository;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByAuthId(String authId);
}
