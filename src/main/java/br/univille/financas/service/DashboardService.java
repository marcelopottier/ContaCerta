package br.univille.financas.service;

import br.univille.financas.dto.*;
import br.univille.financas.model.Movimentacoes;
import br.univille.financas.model.TipoMovimentacao;
import br.univille.financas.model.Users;
import br.univille.financas.repository.MovimentacaoRepository;
import br.univille.financas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private UserRepository userRepository;

    private Users getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public DashboardResumo getResumo() {
        Users currentUser = getCurrentUser();
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        List<Movimentacoes> todasMovimentacoes = movimentacaoRepository.findByUser(currentUser);
        List<Movimentacoes> movimentacoesMes = todasMovimentacoes.stream()
                .filter(m -> !m.getData().isBefore(inicioMes) && !m.getData().isAfter(fimMes))
                .collect(Collectors.toList());

        // Calcular totais do mês
        BigDecimal entradasMes = movimentacoesMes.stream()
                .filter(m -> m.getTipo() == TipoMovimentacao.ENTRADA)
                .map(Movimentacoes::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saidasMes = movimentacoesMes.stream()
                .filter(m -> m.getTipo() == TipoMovimentacao.SAIDA)
                .map(Movimentacoes::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular totais gerais
        BigDecimal entradasTotal = todasMovimentacoes.stream()
                .filter(m -> m.getTipo() == TipoMovimentacao.ENTRADA)
                .map(Movimentacoes::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saidasTotal = todasMovimentacoes.stream()
                .filter(m -> m.getTipo() == TipoMovimentacao.SAIDA)
                .map(Movimentacoes::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoAtual = entradasTotal.subtract(saidasTotal);

        // Última movimentação
        LocalDate ultimaMovimentacao = todasMovimentacoes.stream()
                .map(Movimentacoes::getData)
                .max(LocalDate::compareTo)
                .orElse(null);

        String mesReferencia = hoje.format(DateTimeFormatter.ofPattern("MMMM/yyyy", Locale.forLanguageTag("pt-BR")));

        return new DashboardResumo(
                saldoAtual,
                entradasMes,
                saidasMes,
                entradasTotal,
                saidasTotal,
                todasMovimentacoes.size(),
                ultimaMovimentacao,
                mesReferencia
        );
    }

    public List<GastoPorCategoria> getGastosPorCategoria() {
        Users currentUser = getCurrentUser();
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);

        List<Movimentacoes> saidas = movimentacaoRepository.findByUserAndTipoAndDataGreaterThanEqual(
                currentUser, TipoMovimentacao.SAIDA, inicioMes);

        Map<String, List<Movimentacoes>> gastosPorCategoria = saidas.stream()
                .collect(Collectors.groupingBy(m ->
                        m.getCategoria() != null ? m.getCategoria().getDescricao() : "Sem Categoria"));

        BigDecimal totalGastos = saidas.stream()
                .map(Movimentacoes::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return gastosPorCategoria.entrySet().stream()
                .map(entry -> {
                    String categoria = entry.getKey();
                    List<Movimentacoes> movimentacoes = entry.getValue();

                    BigDecimal valor = movimentacoes.stream()
                            .map(Movimentacoes::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Integer quantidade = movimentacoes.size();

                    Double percentual = totalGastos.compareTo(BigDecimal.ZERO) > 0
                            ? valor.divide(totalGastos, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100)).doubleValue()
                            : 0.0;

                    return new GastoPorCategoria(categoria, valor, quantidade, percentual);
                })
                .sorted((a, b) -> b.getValor().compareTo(a.getValor()))
                .collect(Collectors.toList());
    }

    public MovimentacoesPeriodo getMovimentacoesPeriodo(String periodo) {
        Users currentUser = getCurrentUser();
        LocalDate dataInicio;
        LocalDate dataFim;

        switch (periodo.toLowerCase()) {
            case "semana":
                dataInicio = LocalDate.now().minusDays(7);
                dataFim = LocalDate.now();
                break;
            case "mes":
                YearMonth mesAtual = YearMonth.now();
                dataInicio = mesAtual.atDay(1);
                dataFim = mesAtual.atEndOfMonth();
                break;
            case "trimestre":
                dataInicio = LocalDate.now().minusMonths(3);
                dataFim = LocalDate.now();
                break;
            case "ano":
                dataInicio = LocalDate.now().withDayOfYear(1);
                dataFim = LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear());
                break;
            default:
                dataInicio = LocalDate.now().minusMonths(1);
                dataFim = LocalDate.now();
        }

        List<Movimentacoes> movimentacoes = movimentacaoRepository.findByUserAndDataBetween(
                currentUser, dataInicio, dataFim);

        BigDecimal totalEntradas = movimentacoes.stream()
                .filter(m -> m.getTipo() == TipoMovimentacao.ENTRADA)
                .map(Movimentacoes::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSaidas = movimentacoes.stream()
                .filter(m -> m.getTipo() == TipoMovimentacao.SAIDA)
                .map(Movimentacoes::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldo = totalEntradas.subtract(totalSaidas);

        // Agrupar por dia
        Map<LocalDate, List<Movimentacoes>> movimentacoesPorDia = movimentacoes.stream()
                .collect(Collectors.groupingBy(Movimentacoes::getData));

        List<MovimentacaoDiaria> movimentacoesDiarias = new ArrayList<>();
        LocalDate dataAtual = dataInicio;

        while (!dataAtual.isAfter(dataFim)) {
            List<Movimentacoes> movimentacoesDoDia = movimentacoesPorDia.getOrDefault(dataAtual, new ArrayList<>());

            BigDecimal entradasDia = movimentacoesDoDia.stream()
                    .filter(m -> m.getTipo() == TipoMovimentacao.ENTRADA)
                    .map(Movimentacoes::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saidasDia = movimentacoesDoDia.stream()
                    .filter(m -> m.getTipo() == TipoMovimentacao.SAIDA)
                    .map(Movimentacoes::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saldoDia = entradasDia.subtract(saidasDia);

            movimentacoesDiarias.add(new MovimentacaoDiaria(dataAtual, entradasDia, saidasDia, saldoDia));
            dataAtual = dataAtual.plusDays(1);
        }

        return new MovimentacoesPeriodo(periodo, totalEntradas, totalSaidas, saldo, movimentacoesDiarias);
    }
}
