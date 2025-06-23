package br.univille.financas.controller;

import br.univille.financas.model.Categorias;
import br.univille.financas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriasRepository;

    @GetMapping
    public List<Categorias> getAllCategorias() {
        return categoriasRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorias> getCategoriaById(@PathVariable Long id) {
        Optional<Categorias> categoria = categoriasRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Categorias> createCategoria(@RequestBody Categorias categoria) {
        if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Categorias novaCategoria = categoriasRepository.save(categoria);
        return ResponseEntity.ok(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorias> updateCategoria(@PathVariable Long id, @RequestBody Categorias categoriaDetails) {
        Optional<Categorias> optionalCategoria = categoriasRepository.findById(id);

        if (!optionalCategoria.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (categoriaDetails.getDescricao() == null || categoriaDetails.getDescricao().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Categorias categoria = optionalCategoria.get();
        categoria.setDescricao(categoriaDetails.getDescricao());

        Categorias updatedCategoria = categoriasRepository.save(categoria);
        return ResponseEntity.ok(updatedCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        Optional<Categorias> optionalCategoria = categoriasRepository.findById(id);

        if (!optionalCategoria.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        categoriasRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}