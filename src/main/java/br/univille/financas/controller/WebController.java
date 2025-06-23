package br.univille.financas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login"; // Redirecionar para login inicialmente
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // retorna login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // retorna register.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "index"; // retorna index.html (seu dashboard)
    }

    @GetMapping("/movimentacoes")
    public String movimentacoes() {
        return "movimentacoes"; // retorna movimentacoes.html
    }

    @GetMapping("/categorias")
    public String categorias() {
        return "categorias"; // retorna categorias.html
    }
}