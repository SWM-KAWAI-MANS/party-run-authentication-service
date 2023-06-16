package online.partyrun.partyrunauthenticationservice.domain.token.repository;

import online.partyrun.partyrunauthenticationservice.domain.token.entity.RefreshToken;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {}
