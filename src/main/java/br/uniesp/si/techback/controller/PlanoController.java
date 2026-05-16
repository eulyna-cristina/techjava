package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.service.PlanoService; // Import correto
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
@Slf4j
public class PlanoController {

    // 1. Corrigido: O tipo é PlanoService e o nome da variável é planoService
    private final PlanoService planoService;

    @PostMapping
    public ResponseEntity<Plano> criar(@Valid @RequestBody Plano plano) {
        log.info("Criando novo plano: {}", plano.getNome());
        // 2. Corrigido: Chamando o método 'salvar' do seu Service
        Plano salvo = planoService.salvar(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Plano>> listarTodos() {
        log.info("Listando todos os planos disponíveis");
        // 3. Corrigido: Chamando 'listarTodos' do seu Service
        return ResponseEntity.ok(planoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable UUID id) {
        // 4. Corrigido: Ajustado para usar o buscarPorId do Service
        try {
            Plano plano = planoService.buscarPorId(id);
            return ResponseEntity.ok(plano);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        log.info("Removendo plano ID: {}", id);
        try {
            planoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}