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

//O endpoint para cadastrar um metodo de pagamento agora recebe o DTO completo, incluindo o tipo e os detalhes específicos.
//  do metodo de pagamento. O serviço é responsável por validar e processar essas informações corretamente.

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<MetodoPagamentoDTO> cadastrar(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody MetodoPagamentoDTO dto) { // Adicionado @Valid e alterado para DTO

        log.info("Requisição para cadastrar método de pagamento para o usuário ID: {}", usuarioId);
        MetodoPagamentoDTO salvo = metodoPagamentoService.salvarMetodo(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

// O endpoint para listar os métodos de pagamento de um usuário agora retorna uma lista de DTOs, permitindo que o
// cliente obtenha todas as informações relevantes sobre cada metodo de pagamento associado ao usuário.

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MetodoPagamentoDTO>> listarPorUsuario(@PathVariable UUID usuarioId) {
        log.info("Requisição para listar métodos de pagamento do usuário ID: {}", usuarioId);
        List<MetodoPagamentoDTO> lista = metodoPagamentoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }
}