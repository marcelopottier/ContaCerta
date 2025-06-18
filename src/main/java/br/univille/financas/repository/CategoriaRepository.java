package br.univille.financas.repository;

import br.univille.financas.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categorias, Long> {
}
