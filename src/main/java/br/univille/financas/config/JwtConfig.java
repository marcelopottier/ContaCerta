package br.univille.financas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret:mySecretKeyForContaCertaApplication2024!@#}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24 horas em millisegundos
    private Long expiration;

    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return expiration;
    }
}