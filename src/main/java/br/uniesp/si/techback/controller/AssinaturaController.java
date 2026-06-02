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

    // O endpoint para listar todas as assinaturas agora retorna uma lista de DTOs, permitindo que o cliente obtenha informações
    // detalhadas sobre cada assinatura, incluindo o plano associado, o metodo de pagamento utilizado e o status da assinatura.

    @GetMapping
    public ResponseEntity<List<AssinaturaDTO>> listarTodas() {
        return ResponseEntity.ok(assinaturaService.listarTodas());
    }

    // O endpoint para buscar a assinatura de um usuário específico agora retorna um DTO detalhado,
    // permitindo que o cliente obtenha

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<AssinaturaDTO> buscarPorUsuario(@PathVariable UUID usuarioId) {
        try {
            return ResponseEntity.ok(assinaturaService.buscarPorUsuarioId(usuarioId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // O endpoint para cancelar uma assinatura agora é um DELETE, seguindo as convenções REST. Ele recebe o ID da assinatura a ser
    // cancelada e, se a operação for bem-sucedida, retorna um status 204 No Content. Se a assinatura não for encontrada, retorna um status 404 Not Found.

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