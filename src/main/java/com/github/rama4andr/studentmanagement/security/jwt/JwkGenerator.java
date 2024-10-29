package com.github.rama4andr.studentmanagement.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwkGenerator {

    private final RSAKey rsaJWK;

    public JwkGenerator() throws JOSEException {

        this.rsaJWK = new RSAKeyGenerator(2048)
                .keyID(UUID.randomUUID().toString())
                .generate();
    }

    @Bean
    public RSAKey getRsaJWK() {
        return this.rsaJWK;
    }
}
