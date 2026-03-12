package br.com.systec.opusfinancial.security.impl.repository;


import br.com.systec.opusfinancial.security.impl.entities.SecurityToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecurityTokenRepository extends JpaRepository<SecurityToken, UUID> {
    @Query("Select obj from SecurityToken obj where obj.refreshToken = :refreshToken")
    SecurityToken findByRefreshToken(String refreshToken);

    @Query("SELECT obj from SecurityToken obj where obj.tokenId = :jti and obj.revokedAt is null")
    public Optional<SecurityToken> findByJtiIsValid(String jti);
}
