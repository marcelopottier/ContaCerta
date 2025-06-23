package br.univille.financas.repository;

import br.univille.financas.model.Movimentacoes;
import br.univille.financas.model.TipoMovimentacao;
import br.univille.financas.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacoes, Long> {

    List<Movimentacoes> findByUser(Users user);

    List<Movimentacoes> findByUserAndTipo(Users user, TipoMovimentacao tipo);

    List<Movimentacoes> findByUserAndDataBetween(Users user, LocalDate dataInicio, LocalDate dataFim);

    List<Movimentacoes> findByUserAndTipoAndDataGreaterThanEqual(Users user, TipoMovimentacao tipo, LocalDate data);

    @Query("SELECT m FROM Movimentacoes m WHERE m.user = :user AND m.data >= :dataInicio AND m.data <= :dataFim ORDER BY m.data DESC")
    List<Movimentacoes> findMovimentacoesPorPeriodo(@Param("user") Users user,
                                                    @Param("dataInicio") LocalDate dataInicio,
                                                    @Param("dataFim") LocalDate dataFim);
}