package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.service.ConteudoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID; // IMPORTANTE: Usar UUID conforme a especificação

@RestController
@RequestMapping("/conteudos") // Alterado de /filmes para /conteudos (mais genérico)
@RequiredArgsConstructor
@Slf4j
public class ConteudoController {

    private final ConteudoService conteudoService;

    @GetMapping("/ordenado")
    public List<Conteudo> listarOrdenado() {
        log.info("Listando conteúdos ordenados por título");
        return conteudoService.listarOrdenado();
    }

    @GetMapping
    public List<ConteudoDTO> listar() {
        log.info("Listando todos os conteúdos (Filmes e Séries)");
        return conteudoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoDTO> buscarPorId(@PathVariable UUID id) { // Alterado para UUID
        try {
            ConteudoDTO dto = conteudoService.buscarPorId(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Erro ao buscar conteúdo ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ConteudoDTO> criar(@Valid @RequestBody ConteudoDTO conteudoDTO) {
        log.info("Criando novo conteúdo: {}", conteudoDTO.getTitulo());
        try {
            ConteudoDTO salvo = conteudoService.salvar(conteudoDTO);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(salvo.getId())
                    .toUri();
            return ResponseEntity.created(location).body(salvo);
        } catch (Exception e) {
            log.error("Erro ao criar conteúdo: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConteudoDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody ConteudoDTO conteudoDTO) {
        log.info("Atualizando conteúdo ID: {}", id);
        try {
            ConteudoDTO atualizado = conteudoService.atualizar(id, conteudoDTO);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) { // Alterado para UUID
        log.info("Excluindo conteúdo ID: {}", id);
        try {
            conteudoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}