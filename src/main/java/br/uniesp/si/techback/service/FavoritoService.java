package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.FavoritoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import br.uniesp.si.techback.repository.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuariosRepository;
    private final ConteudoRepository conteudoRepository;

    @Transactional
    public Favorito salvar(UUID usuarioId, UUID conteudoId) {
        log.info("Processando salvamento de favorito para o usuário: {}", usuarioId);

        // Busca o usuário ou lança erro
        Usuario usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        // Busca o conteúdo ou lança erro
        Conteudo conteudo = conteudoRepository.findById(conteudoId)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado com o ID: " + conteudoId));

        // Cria e popula o objeto Favorito
        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setConteudo(conteudo);

        return favoritoRepository.save(favorito);
    }

    public List<Favorito> listarPorPreferencia(UUID usuarioId) {
        log.info("Listando preferências do usuário: {}", usuarioId);
        // Busca no repositório usando o método customizado
        return favoritoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public void excluir(UUID id) {
        log.info("Removendo favorito ID: {}", id);
        if (!favoritoRepository.existsById(id)) {
            throw new RuntimeException("Favorito não encontrado.");
        }
        favoritoRepository.deleteById(id);
    }
}