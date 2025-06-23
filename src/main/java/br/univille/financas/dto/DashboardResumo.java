package br.univille.financas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResumo {
    private BigDecimal saldoAtual;
    private BigDecimal entradasMes;
    private BigDecimal saidasMes;
    private BigDecimal entradasTotal;
    private BigDecimal saidasTotal;
    private Integer totalMovimentacoes;
    private LocalDate ultimaMovimentacao;
    private String mesReferencia;
}
