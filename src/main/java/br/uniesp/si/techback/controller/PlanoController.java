package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
// Controlador REST para gerenciar funcionamento da classe. isso são notação do springboot.
@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
@Slf4j
public class PlanoController {

    private final PlanoService planoService;

    @PostMapping // Criar um novo plano
    public ResponseEntity<PlanoDTO> criar(@Valid @RequestBody PlanoDTO dto) {
        log.info("Requisição para criar plano recebida: {}", dto.getNome());
        PlanoDTO salvo = planoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping // Listar todos os planos disponíveis
    public ResponseEntity<List<PlanoDTO>> listarTodos() {
        log.info("Requisição para listar todos os planos");
        return ResponseEntity.ok(planoService.listarTodos());
    }

    @GetMapping("/{id}") // Buscar um plano específico por ID
    public ResponseEntity<PlanoDTO> buscarPorId(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(planoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint adicional para demonstrar o filtro JPQL personalizado na apresentação
    // o usuario pode buscar planos com preço menor ou igual a um valor específico
    @GetMapping("/filtro-preco")
    public ResponseEntity<List<PlanoDTO>> buscarPorPrecoMaximo(@RequestParam BigDecimal precoMaximo) {
        return ResponseEntity.ok(planoService.listarPorPrecoMaximo(precoMaximo));
    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        log.info("Requisição para remover plano ID: {}", id);
        try {
            planoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}