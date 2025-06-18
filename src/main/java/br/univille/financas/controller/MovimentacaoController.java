package br.univille.financas.controller;

import br.univille.financas.model.Movimentacoes;
import br.univille.financas.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacoesRepository;

    @GetMapping
    public List<Movimentacoes> getAllMovimentacoes() {
        return movimentacoesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimentacoes> getMovimentacaoById(@PathVariable Long id) {
        Optional<Movimentacoes> movimentacao = movimentacoesRepository.findById(id);
        if (movimentacao.isPresent()) {
            return ResponseEntity.ok(movimentacao.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Movimentacoes createMovimentacao(@RequestBody Movimentacoes movimentacao) {
        return movimentacoesRepository.save(movimentacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimentacoes> updateMovimentacao(@PathVariable Long id, @RequestBody Movimentacoes movimentacaoDetails) {
        Optional<Movimentacoes> optionalMovimentacao = movimentacoesRepository.findById(id);
        if (!optionalMovimentacao.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Movimentacoes movimentacao = optionalMovimentacao.get();

        movimentacao.setValor(movimentacaoDetails.getValor());
        movimentacao.setData(movimentacaoDetails.getData());
        movimentacao.setDescricao(movimentacaoDetails.getDescricao());
        movimentacao.setCategoria(movimentacaoDetails.getCategoria());
        movimentacao.setUser(movimentacaoDetails.getUser());

        Movimentacoes updatedMovimentacao = movimentacoesRepository.save(movimentacao);
        return ResponseEntity.ok(updatedMovimentacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimentacao(@PathVariable Long id) {
        Optional<Movimentacoes> optionalMovimentacao = movimentacoesRepository.findById(id);
        if (!optionalMovimentacao.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        movimentacoesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
