package br.univille.financas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoDiaria {
    private LocalDate data;
    private BigDecimal entradas;
    private BigDecimal saidas;
    private BigDecimal saldo;
}
