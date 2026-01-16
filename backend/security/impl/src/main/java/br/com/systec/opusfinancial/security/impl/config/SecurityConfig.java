package br.com.systec.opusfinancial.security.impl.config;

import br.com.systec.opusfinancial.security.impl.filter.SecurityFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Usa lambda para configurar gerenciamento de sessão
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/v1/auth/**", "/v1/user-accounts/create").permitAll()
                        .requestMatchers(
                                "/**.html",
                                "/v3/**",
                                "/webjars/**",
                                "/configuration/**",
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/actuator/**",
                                "/manager/**",
                                "/notification/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, excep) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                            res.getWriter().write("{\"error\": \"Token inválido ou expirado\"}");
                        })
                        .accessDeniedHandler((req, res, excep) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                            res.getWriter().write("{\"error\": \"Acesso negado\"}");
                        })
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
