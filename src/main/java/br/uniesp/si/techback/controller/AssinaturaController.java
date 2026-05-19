package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.service.AssinaturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assinaturas")
@RequiredArgsConstructor
@Slf4j
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    // ATUALIZADO: Agora recebe o pagamento na URL para bater com as regras do Service
    @PostMapping("/usuario/{usuarioId}/plano/{planoId}/pagamento/{metodoPagamentoId}")
    public ResponseEntity<Assinatura> criar(
            @PathVariable UUID usuarioId,
            @PathVariable UUID planoId,
            @PathVariable UUID metodoPagamentoId) { // <-- Adicionado aqui

        log.info("Criando assinatura para o usuário {} no plano {} com o pagamento {}", usuarioId, planoId, metodoPagamentoId);

        // Agora os 3 parâmetros combinam perfeitamente com o Service!
        Assinatura salva = assinaturaService.criarAssinatura(usuarioId, planoId, metodoPagamentoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // Listando todas as assinaturas do sistema
    @GetMapping
    public ResponseEntity<List<Assinatura>> listarTodas() {
        return ResponseEntity.ok(assinaturaService.listarTodas());
    }

    // Buscar a assinatura ativa de um usuário específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Assinatura> buscarPorUsuario(@PathVariable UUID usuarioId) {
        try {
            Assinatura assinatura = assinaturaService.buscarPorUsuarioId(usuarioId);
            return ResponseEntity.ok(assinatura);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {
        log.info("Cancelando assinatura ID: {}", id);
        try {
            assinaturaService.cancelarAssinatura(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}