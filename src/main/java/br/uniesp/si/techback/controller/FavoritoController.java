package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
@Slf4j
public class FavoritoController {

    private final FavoritoService favoritoService;

    // Endpoint para favoritar: POST /favoritos/usuario/{uId}/conteudo/{cId}
    @PostMapping("/usuario/{usuarioId}/conteudo/{conteudoId}")
    public ResponseEntity<FavoritoDTO> adicionarFavorito(
            @PathVariable UUID usuarioId,
            @PathVariable UUID conteudoId) {

        log.info("Usuário {} favoritando conteúdo {}", usuarioId, conteudoId);
        FavoritoDTO salvo = favoritoService.salvar(usuarioId, conteudoId);
        return ResponseEntity.ok(salvo);
    }

    // Lista todos os favoritos de um usuário específico (Ordenados por data desc conforme especificação)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoDTO>> listarPorUsuario(@PathVariable UUID usuarioId) {
        log.info("Listando favoritos do usuário {}", usuarioId);
        List<FavoritoDTO> favoritos = favoritoService.listarPorPreferencia(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    // Remove um favorito usando a PK composta da especificação
    @DeleteMapping("/usuario/{usuarioId}/conteudo/{conteudoId}")
    public ResponseEntity<Void> removerFavorito(
            @PathVariable UUID usuarioId,
            @PathVariable UUID conteudoId) {

        log.info("Usuário {} removendo conteúdo {} dos favoritos", usuarioId, conteudoId);
        favoritoService.excluir(usuarioId, conteudoId);
        return ResponseEntity.noContent().build();
    }
}