package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.service.MetodoPagamentoService; // Você precisará criar esse service se ainda não existir
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

    // Endpoint para cadastrar um novo método de pagamento tokenizado para o usuário
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<MetodoPagamento> cadastrar(
            @PathVariable UUID usuarioId,
            @RequestBody MetodoPagamento metodoPagamento) {

        log.info("Cadastrando método de pagamento para o usuário ID: {}", usuarioId);
        MetodoPagamento salvo = metodoPagamentoService.salvarMetodo(usuarioId, metodoPagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // Endpoint para listar as formas de pagamento salvas de um usuário específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MetodoPagamento>> listarPorUsuario(@PathVariable UUID usuarioId) {
        log.info("Buscando métodos de pagamento do usuário ID: {}", usuarioId);
        List<MetodoPagamento> lista = metodoPagamentoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }
}