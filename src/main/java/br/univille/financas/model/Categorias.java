package br.univille.financas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
}
