package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.AssinaturaDTO;
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

    @PostMapping("/usuario/{usuarioId}/plano/{planoId}/pagamento/{metodoPagamentoId}")
    public ResponseEntity<AssinaturaDTO> criar(
            @PathVariable UUID usuarioId,
            @PathVariable UUID planoId,
            @PathVariable UUID metodoPagamentoId) {

        log.info("Requisição POST para assinar recebida.");
        AssinaturaDTO salva = assinaturaService.criarAssinatura(usuarioId, planoId, metodoPagamentoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<List<AssinaturaDTO>> listarTodas() {
        return ResponseEntity.ok(assinaturaService.listarTodas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<AssinaturaDTO> buscarPorUsuario(@PathVariable UUID usuarioId) {
        try {
            return ResponseEntity.ok(assinaturaService.buscarPorUsuarioId(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {
        log.info("Requisição DELETE para a assinatura ID: {}", id);
        try {
            assinaturaService.cancelarAssinatura(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}