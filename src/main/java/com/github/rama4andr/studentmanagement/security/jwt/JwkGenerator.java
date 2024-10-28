package com.github.rama4andr.studentmanagement.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JwkGenerator {

    private final RSAKey rsaJWK;

    public JwkGenerator() throws JOSEException {

        this.rsaJWK = new RSAKeyGenerator(2048)
                .keyID("123")
                .generate();
    }

    @Bean
    public RSAKey getRsaJWK() {
        return this.rsaJWK;
    }
}
