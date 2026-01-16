package br.com.systec.opusfinancial.security.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.services.UserService;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.security.api.exceptions.RefreshTokenExpiredException;
import br.com.systec.opusfinancial.security.api.exceptions.RefreshTokenRevokeException;
import br.com.systec.opusfinancial.security.api.exceptions.RefreshTokenUsedException;
import br.com.systec.opusfinancial.security.api.exceptions.TokenGenerationFailedException;
import br.com.systec.opusfinancial.security.api.exceptions.TokenOpusfinancialExpiredException;
import br.com.systec.opusfinancial.security.api.exceptions.TokenRevokeException;
import br.com.systec.opusfinancial.security.api.service.SecurityTokenService;
import br.com.systec.opusfinancial.security.api.vo.LoginAuthenticateVO;
import br.com.systec.opusfinancial.security.impl.entities.SecurityToken;
import br.com.systec.opusfinancial.security.impl.repository.SecurityTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityTokenServiceImpl implements SecurityTokenService {
    private static final Logger log = LoggerFactory.getLogger(SecurityTokenServiceImpl.class);
    public static final String TENANT_CLAIM = "tenant-id";

    private static final String ISSUE = "opus.api";
    private static final String AUDIENCE = "opus.web";

    @Value("${api.security.token.secret}")
    private String secret;
    private final SecurityTokenRepository repository;
    private final UserService userService;


    public SecurityTokenServiceImpl(SecurityTokenRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LoginAuthenticateVO generateNewAccessTokenByRefreshToken(String refreshToken) throws BaseException {
        try {
            SecurityToken olSecurityToken = repository.findByRefreshToken(refreshToken);

            if (olSecurityToken.getLastUsedAt() != null) {
                throw new RefreshTokenUsedException();
            }

            if (olSecurityToken.getRevokedAt() != null) {
                throw new RefreshTokenRevokeException();
            }

            if (olSecurityToken.getExpiresAt().isBefore(Instant.now())) {
                throw new RefreshTokenExpiredException();
            }

            olSecurityToken.setLastUsedAt(Instant.now());
            repository.update(olSecurityToken);

            UserVO user = userService.findById(olSecurityToken.getUserId());

            return generaTokenAndRefreshTokenBUser(user);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar gerar o refresh token", e);
            throw new BaseException("Erro ao tentar gerar o refresh token", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LoginAuthenticateVO generateTokenAndRefreshToken(Authentication authentication) throws SecurityException {
        try {
            UserVO user = (UserVO) authentication.getPrincipal();
            return generaTokenAndRefreshTokenBUser(user);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
          log.error("Ocorreu um erro ao tentar gerar os tokens", e);
          throw new SecurityException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getSubject(String tokenJWT) throws SecurityException {
        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                    .withIssuer(ISSUE)
                    .withAudience(AUDIENCE)
                    .build()
                    .verify(tokenJWT);

            return decodedJWT.getSubject();
        } catch (TokenExpiredException e) {
            throw new TokenOpusfinancialExpiredException(e);
        } catch (Exception e) {
            throw new BaseException(e.getMessage(), e);
        }
    }

    private String generateToken(UserVO user, String jti) {

        return JWT.create()
                .withIssuer(ISSUE)
                .withSubject(user.getUsername())
                .withAudience(AUDIENCE)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(generateDateExpired())
                .withClaim(TENANT_CLAIM, user.getTenantId().toString())
                .withJWTId(jti)
                .sign(getAlgorithm());
    }

    private String generateRefreshToken() throws BaseException {
        try {
            byte[] bytes = new byte[64];
            new SecureRandom().nextBytes(bytes);
            String refreshToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

            // Hash com SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(refreshToken.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void validateTokenRevoke(String tokenJWT) throws BaseException {
        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                    .withIssuer(ISSUE)
                    .withAudience(AUDIENCE)
                    .build()
                    .verify(tokenJWT);

            Optional<SecurityToken> securityToken = repository.findByJtiIsValid(decodedJWT.getId());

            if (securityToken.isEmpty()) {
                throw new TokenRevokeException();
            }

        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (TokenExpiredException e) {
            log.error("Token expirado", e);
            throw new TokenOpusfinancialExpiredException();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar validar o token", e);
            throw new BaseException(e.getMessage(), e);
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Instant generateDateExpired() {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));
    }

    private LoginAuthenticateVO generaTokenAndRefreshTokenBUser(UserVO user) throws BaseException {
        try {
            String jti = UUID.randomUUID().toString();
            String accessToken = generateToken(user, jti);
            String refreshToken = generateRefreshToken();

            SecurityToken securityToken = new SecurityToken();
            securityToken.setUserId(user.getId());
            securityToken.setTokenId(jti);
            securityToken.setCreateAt(new Date());
            securityToken.setExpiresAt(Instant.now().plusSeconds(60L * 60 * 24 * 14));
            securityToken.setRefreshToken(refreshToken);
            securityToken.setTenantId(user.getTenantId());

            repository.save(securityToken);

            String profileName = "";

//            if(user.getRole() != null && !user.getRole().isEmpty()) {
//                Optional<RoleVO> profile = user.getRole().stream().findFirst();
//                if(profile.isPresent()) {
//                    profileName = profile.get().getName();
//                }
//            }

            return new LoginAuthenticateVO(user.getId(), accessToken, "Bearer", profileName, refreshToken);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar gera o toke e o refresh token");
            throw new TokenGenerationFailedException(e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UUID getTenantId(String token) throws SecurityException {
        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                    .withIssuer(ISSUE)
                    .withAudience(AUDIENCE)
                    .build()
                    .verify(token);
            String tenantId = decodedJWT.getClaim(TENANT_CLAIM).asString();

            return UUID.fromString(tenantId);
        } catch (Exception e) {
            log.error("Ocoreu um erro ao tentar pagar o tenantId", e);
            throw new SecurityException("Erro ao tentar pegar o tenantId", e);
        }
    }
}
