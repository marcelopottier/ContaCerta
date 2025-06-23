package br.univille.financas.controller;

import br.univille.financas.dto.DashboardResumo;
import br.univille.financas.dto.GastoPorCategoria;
import br.univille.financas.dto.MovimentacoesPeriodo;
import br.univille.financas.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/resumo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<DashboardResumo> getResumo() {
        try {
            DashboardResumo resumo = dashboardService.getResumo();
            return ResponseEntity.ok(resumo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/gastos-por-categoria")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<GastoPorCategoria>> getGastosPorCategoria() {
        try {
            List<GastoPorCategoria> gastos = dashboardService.getGastosPorCategoria();
            return ResponseEntity.ok(gastos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/movimentacoes-periodo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MovimentacoesPeriodo> getMovimentacoesPeriodo(
            @RequestParam(defaultValue = "mes") String periodo) {
        try {
            MovimentacoesPeriodo movimentacoes = dashboardService.getMovimentacoesPeriodo(periodo);
            return ResponseEntity.ok(movimentacoes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/estatisticas")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getEstatisticas() {
        try {
            // Coletar todas as informações primeiro
            DashboardResumo resumo = dashboardService.getResumo();
            List<GastoPorCategoria> gastos = dashboardService.getGastosPorCategoria();
            MovimentacoesPeriodo movimentacoesMes = dashboardService.getMovimentacoesPeriodo("mes");

            // Criar mapa com as estatísticas
            Map<String, Object> estatisticas = new HashMap<>();
            estatisticas.put("resumo", resumo);
            estatisticas.put("gastosPorCategoria", gastos);
            estatisticas.put("movimentacoesMes", movimentacoesMes);

            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}