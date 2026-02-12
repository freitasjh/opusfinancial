package br.com.systec.opusfinancial.security.impl.filter;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.commons.security.TenantContext;
import br.com.systec.opusfinancial.security.api.exceptions.SecurityException;
import br.com.systec.opusfinancial.security.api.service.AuthenticationService;
import br.com.systec.opusfinancial.security.api.service.SecurityTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    private final SecurityTokenService securityTokenService;
    private final AuthenticationService authenticationService;

    public SecurityFilter(SecurityTokenService securityTokenService, AuthenticationService authenticationService) {
        this.securityTokenService = securityTokenService;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws BaseException {
        try {
            log.warn("@@@ Iniciando filtro de segurança");
            String bearerToken = recoverToken(request);

            if (bearerToken != null) {
                securityTokenService.validateTokenRevoke(bearerToken);
                var username = securityTokenService.getSubject(bearerToken);
                var userDetails = authenticationService.loadUserByUsername(username);
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                TenantContext.runWithTenant(securityTokenService.getTenantId(bearerToken), () -> {
                    filterChain.doFilter(request, response);
                    return null;
                });
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao tentar fazer o SecurityFilter", e);
            throw new SecurityException(e);
        } finally {
            log.warn("@@@ Finalizando o secuirty service");
            SecurityContextHolder.clearContext();
        }
    }

    private String recoverToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token == null || !token.startsWith("Bearer ")){
            return null;
        }

        return token.replace("Bearer ", "");
    }
}
