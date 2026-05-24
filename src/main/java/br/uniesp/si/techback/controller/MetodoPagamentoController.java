package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.service.MetodoPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Slf4j
public class MetodoPagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<MetodoPagamentoDTO> cadastrar(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody MetodoPagamentoDTO dto) { // Adicionado @Valid e alterado para DTO

        log.info("Requisição para cadastrar método de pagamento para o usuário ID: {}", usuarioId);
        MetodoPagamentoDTO salvo = metodoPagamentoService.salvarMetodo(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MetodoPagamentoDTO>> listarPorUsuario(@PathVariable UUID usuarioId) {
        log.info("Requisição para listar métodos de pagamento do usuário ID: {}", usuarioId);
        List<MetodoPagamentoDTO> lista = metodoPagamentoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }
}