package com.github.rama4andr.studentmanagement.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RSAKey rsaKey;

    public SecurityConfig(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configurer) throws Exception {
        return configurer.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/auth", "/auth/create")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));

        return httpSecurity.build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        try {
            RSAKey jwk = new RSAKey.Builder(rsaKey.toRSAPublicKey())
                    .privateKey(rsaKey.toRSAPrivateKey())
                    .keyID(UUID.randomUUID().toString())
                    .build();

            JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
            return new NimbusJwtEncoder(jwkSource);
        } catch (JOSEException e) {
            throw new IllegalStateException("Failed to create JwtEncoder with the specified public key", e);
        }
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        try {
            return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        } catch (JOSEException e) {
            throw new IllegalStateException("Failed to create JwtDecoder with the specified public key", e);
        }
    }

}
