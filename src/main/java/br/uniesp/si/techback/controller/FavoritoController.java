package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Favorito;
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
    public ResponseEntity<Favorito> adicionarFavorito(
            @PathVariable UUID usuarioId,
            @PathVariable UUID conteudoId) {

        log.info("Usuário {} favoritando conteúdo {}", usuarioId, conteudoId);
        Favorito salvo = favoritoService.salvar(usuarioId, conteudoId);
        return ResponseEntity.ok(salvo);
    }

    // Lista todos os favoritos de um usuário específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favorito>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(favoritoService.listarPorPreferencia(usuarioId));
    }

    // Remove um favorito
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFavorito(@PathVariable UUID id) {
        favoritoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}