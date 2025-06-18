package br.univille.financas.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // liberar acesso ao console H2
                        .requestMatchers("/h2-console/**").permitAll()
                        // liberar outras URLs conforme seu projeto
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        // desabilitar CSRF para o console H2
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        // permitir iframe para o console H2 funcionar
                        .frameOptions().sameOrigin()
                )
                .formLogin();  // mantém login para o resto do app

        return http.build();
    }
}
