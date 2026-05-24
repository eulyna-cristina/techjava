package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.FavoritoId;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuariosRepository;
    private final ConteudoRepository conteudoRepository;

    @Transactional
    public FavoritoDTO salvar(UUID usuarioId, UUID conteudoId) {
        log.info("Processando salvamento de favorito para o usuário: {} e conteúdo: {}", usuarioId, conteudoId);

        // 1. Cria a chave composta exigida pela especificação
        FavoritoId favoritoId = new FavoritoId(usuarioId, conteudoId);

        // Se o usuário já favoritou esse conteúdo, apenas retorna o existente para evitar duplicidade
        if (favoritoRepository.existsById(favoritoId)) {
            log.info("Conteúdo já está favoritado pelo usuário.");
            Favorito existente = favoritoRepository.findById(favoritoId).get();
            return converterParaDTO(existente);
        }

        // Busca o usuário ou lança erro
        Usuario usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        // Busca o conteúdo ou lança erro
        Conteudo conteudo = conteudoRepository.findById(conteudoId)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado com o ID: " + conteudoId));

        // 2. Cria e popula o objeto Favorito utilizando o padrão Builder do Lombok
        Favorito favorito = Favorito.builder()
                .id(favoritoId)
                .usuario(usuario)
                .conteudo(conteudo)
                .build();

        Favorito salvo = favoritoRepository.save(favorito);
        return converterParaDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<FavoritoDTO> listarPorPreferencia(UUID usuarioId) {
        log.info("Listando preferências do usuário: {}", usuarioId);

        // 3. Busca utilizando a JPQL customizada (Item 5 da especificação: Ordenado por data desc)
        List<Favorito> favoritos = favoritoRepository.listarFavoritosRecentes(usuarioId);

        return favoritos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluir(UUID usuarioId, UUID conteudoId) {
        log.info("Removendo favorito para Usuário: {} e Conteúdo: {}", usuarioId, conteudoId);

        // 4. Monta a PK composta para realizar a exclusão
        FavoritoId favoritoId = new FavoritoId(usuarioId, conteudoId);

        if (!favoritoRepository.existsById(favoritoId)) {
            throw new RuntimeException("Favorito não encontrado para este usuário e conteúdo.");
        }

        favoritoRepository.deleteById(favoritoId);
    }

    // Método auxiliar de conversão Entity -> DTO para manter a arquitetura limpa
    private FavoritoDTO converterParaDTO(Favorito favorito) {
        return FavoritoDTO.builder()
                .usuarioId(favorito.getId().getUsuarioId())
                .conteudoId(favorito.getId().getConteudoId())
                .conteudoTitulo(favorito.getConteudo().getTitulo())
                .conteudoTipo(favorito.getConteudo().getTipo())
                .criadoEm(favorito.getCriadoEm())
                .build();
    }
}