package br.univille.financas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private LocalDate data;
    private String descricao;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private User user;
}
