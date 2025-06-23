package br.univille.financas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastoPorCategoria {
    private String categoria;
    private BigDecimal valor;
    private Integer quantidade;
    private Double percentual;
}
