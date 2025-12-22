package br.com.systec.opusfinancial.security.impl.repository;

import br.com.systec.opusfinancial.commons.repository.AbstractRepository;
import br.com.systec.opusfinancial.security.impl.entities.SecurityToken;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SecurityTokenRepository extends AbstractRepository<SecurityToken, UUID> {
    @Transactional(propagation = Propagation.SUPPORTS)
    public SecurityToken findByRefreshToken(String refreshToken) {
        String sql = "Select obj from SecurityToken obj where obj.refreshToken = :refreshToken";

        TypedQuery<SecurityToken> query = getEntityManager().createQuery(sql, SecurityToken.class);
        query.setParameter("refreshToken", refreshToken);

        return query.getSingleResult();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<SecurityToken> findByJtiIsValid(String jti) {
        String sql = "SELECT obj from SecurityToken obj where obj.tokenId = :jti and obj.revokedAt is null";

        TypedQuery<SecurityToken> query = getEntityManager().createQuery(sql, SecurityToken.class);
        query.setParameter("jti", jti);

        if(query.getResultList().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(query.getSingleResult());
    }
}
