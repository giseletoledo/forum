package com.oracleone.forumhub.controller;

import com.oracleone.forumhub.dto.TopicoDTO;
import com.oracleone.forumhub.service.TopicoService;
import com.oracleone.forumhub.utils.TopicoConverter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {


    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @GetMapping
    public ResponseEntity<List<TopicoDTO>> listarTopicos() {
        List<TopicoDTO> topicos = topicoService.listarTopicos();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> detalharTopico(@PathVariable Long id) {
        TopicoDTO topico = topicoService.detalharTopico(id);
        return ResponseEntity.ok(topico);
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> criarTopico(@RequestBody @Valid TopicoDTO topicoDTO) {
        TopicoDTO novoTopico = topicoService.criarTopico(topicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoDTO topicoDTO) {
        TopicoDTO topicoAtualizado = topicoService.atualizarTopico(id, topicoDTO);
        return ResponseEntity.ok(topicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTopico(@PathVariable Long id) {
        topicoService.excluirTopico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<TopicoDTO> getTopicosByCursoAndYear(@RequestParam String curso, @RequestParam int year) {
        return topicoService.findTopicosByCursoAndYear(curso, year)
                .stream()
                .map(TopicoConverter::toDTO)  // Use the TopicoConverter to map Topico to TopicoDTO
                .toList();
    }
}
