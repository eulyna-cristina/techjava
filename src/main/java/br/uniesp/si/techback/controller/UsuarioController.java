package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController { // Nome da classe conforme você pediu

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        log.info("Iniciando cadastro do usuário: {}", usuario.getEmail());
        Usuario salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        log.info("Listando todos os usuários cadastrados");
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable UUID id) {
        log.info("Buscando usuário pelo ID: {}", id);
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        log.info("Removendo usuário ID: {}", id);
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /* MÉTODO ESPECIAL: Assinar um Plano
       Esse método liga o Usuário ao Plano escolhido (Mensal, Premium, etc)
    */
    @PatchMapping("/{usuarioId}/assinar/{planoId}")
    public ResponseEntity<Usuario> vincularPlano(
            @PathVariable UUID usuarioId,
            @PathVariable UUID planoId) {
        log.info("Vinculando usuário ID {} ao plano ID {}", usuarioId, planoId);
        Usuario usuarioAtualizado = usuarioService.vincularPlano(usuarioId, planoId);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}