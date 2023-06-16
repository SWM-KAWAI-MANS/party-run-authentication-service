package online.partyrun.partyrunauthenticationservice.domain.member.repository;

import online.partyrun.partyrunauthenticationservice.domain.member.entity.Member;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    Optional<Member> findByAuthId(String authId);
}
