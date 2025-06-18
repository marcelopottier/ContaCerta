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
    public Categorias createCategoria(@RequestBody Categorias categoria) {
        return categoriasRepository.save(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorias> updateCategoria(@PathVariable Long id, @RequestBody Categorias categoriaDetails) {
        Optional<Categorias> optionalCategoria = categoriasRepository.findById(id);

    }
