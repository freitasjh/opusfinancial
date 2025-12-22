package br.com.systec.opusfinancial.security.impl.entities;

import br.com.systec.opusfinancial.commons.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "security_token")
public class SecurityToken extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 6314637557580339662L;

    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "token_id")
    private String tokenId;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "tenant_id")
    private UUID tenantId;
    @Column(name = "expired_at")
    private Instant expiresAt;
    @Column(name = "revoke_at")
    private Instant revokedAt;
    @Column(name = "last_used_at")
    private Instant lastUsedAt;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public Instant getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }
}
