package br.univille.financas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacoesPeriodo {
    private String periodo;
    private BigDecimal totalEntradas;
    private BigDecimal totalSaidas;
    private BigDecimal saldo;
    private List<MovimentacaoDiaria> movimentacoesDiarias;
}