package br.univille.financas.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Movimentacoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private LocalDate data;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;
    
    @ManyToOne
    private Categorias categoria;

    @ManyToOne
    private Users user;
}
