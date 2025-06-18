package br.univille.financas.repository;

import br.univille.financas.model.Movimentacoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacoes, Long> {
}
