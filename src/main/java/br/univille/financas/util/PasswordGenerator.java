package br.univille.financas.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Gerar hash para admin123
        String password = "admin123";
        String hash = encoder.encode(password);

        System.out.println("Senha: " + password);
        System.out.println("Hash: " + hash);

        // Verificar se o hash funciona
        boolean matches = encoder.matches(password, hash);
        System.out.println("Hash válido: " + matches);

        // Testar com o hash atual do banco
        String hashDoBanco = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVzwMS";
        boolean matchesBanco = encoder.matches(password, hashDoBanco);
        System.out.println("Hash do banco válido: " + matchesBanco);
    }
}