package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ENDPOINT 1 OBRIGATÓRIO: Cadastro de usuário com validação do DTO
    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody UsuarioDTO dto) {
        log.info("[START] POST /usuarios - Iniciando cadastro do usuário com e-mail: {}", dto.getEmail());

        // Passa o DTO para o Service tratar a API externa, BCrypt e salvar
        Usuario salvo = usuarioService.salvar(dto);

        log.info("[END] POST /usuarios - Usuário cadastrado com sucesso. ID: {}", salvo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // ENDPOINT 2 OBRIGATÓRIO: Listagem (Sem paginação, mas ordenada por nome via Service)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        log.info("[START] GET /usuarios - Requisitando lista de todos os usuários.");

        List<Usuario> listaOrdenada = usuarioService.listarTodos();

        log.info("[END] GET /usuarios - Retornando {} usuários ordenados por nome.", listaOrdenada.size());
        return ResponseEntity.ok(listaOrdenada);
    }

    // Os endpoints abaixo não eram estritamente obrigatórios para a sua dupla,
    // mas já que estão prontos, vale a pena manter para valorizar o projeto!
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable UUID id) {
        log.info("[START] GET /usuarios/{} - Buscando usuário pelo ID.", id);
        return usuarioService.buscarPorId(id)
                .map(usuario -> {
                    log.info("[END] GET /usuarios/{} - Usuário encontrado.", id);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> {
                    log.warn("[END] GET /usuarios/{} - Usuário não encontrado.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        log.info("[START] DELETE /usuarios/{} - Solicitando exclusão.", id);
        usuarioService.excluir(id);
        log.info("[END] DELETE /usuarios/{} - Processo de exclusão finalizado.", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{usuarioId}/assinar/{planoId}")
    public ResponseEntity<Usuario> vincularPlano(
            @PathVariable UUID usuarioId,
            @PathVariable UUID planoId) {
        log.info("[START] PATCH /usuarios/{}/assinar/{} - Iniciando vínculo de plano.", usuarioId, planoId);
        Usuario usuarioAtualizado = usuarioService.vincularPlano(usuarioId, planoId);
        log.info("[END] PATCH /usuarios/{}/assinar/{} - Plano vinculado com sucesso.", usuarioId, planoId);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}