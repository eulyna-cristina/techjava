package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.ConteudoDTO;
import br.uniesp.si.techback.mapper.ConteudoMapper;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.repository.ConteudoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConteudoService {

    private final ConteudoRepository conteudoRepository;
    private final ConteudoMapper conteudoMapper;

    // CORRIGIDO: Retornando DTO e usando o método atualizado do Repository
    public List<ConteudoDTO> listarOrdenado() {
        log.info("Buscando conteúdos ordenados por título");
        return conteudoRepository.listarConteudosOrdenados().stream()
                .map(conteudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // NOVO: Método para processar a busca com filtros dinâmicos (JPQL)
    public List<ConteudoDTO> listarComFiltros(String termo, String tipo, String genero) {
        log.info("Filtrando conteúdos no service - Termo: {}, Tipo: {}, Gênero: {}", termo, tipo, genero);
        List<Conteudo> listaFiltrada = conteudoRepository.buscarComFiltros(termo, tipo, genero);
        return listaFiltrada.stream()
                .map(conteudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ConteudoDTO> listar() {
        log.info("Buscando todos os conteúdos cadastrados");
        try {
            List<Conteudo> lista = conteudoRepository.findAll();
            return lista.stream()
                    .map(conteudoMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Falha ao buscar conteúdos: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ConteudoDTO buscarPorId(UUID id) {
        log.info("Buscando conteúdo pelo ID: {}", id);
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> {
                    String mensagem = "Conteúdo não encontrado com o ID: " + id;
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return conteudoMapper.toDTO(conteudo);
    }

    @Transactional
    public ConteudoDTO atualizar(UUID id, ConteudoDTO conteudoDTO) {
        log.info("Atualizando conteúdo ID: {}", id);
        return conteudoRepository.findById(id)
                .map(existente -> {
                    conteudoDTO.setId(id);
                    Conteudo paraAtualizar = conteudoMapper.toEntity(conteudoDTO);
                    Conteudo salvo = conteudoRepository.save(paraAtualizar);
                    log.info("Conteúdo ID: {} atualizado com sucesso", id);
                    return conteudoMapper.toDTO(salvo);
                })
                .orElseThrow(() -> new RuntimeException("Falha ao atualizar: ID não encontrado: " + id));
    }

    @Transactional
    public ConteudoDTO salvar(ConteudoDTO conteudoDTO) {
        log.info("Salvando novo conteúdo: {}", conteudoDTO.getTitulo());
        try {
            Conteudo entidade = conteudoMapper.toEntity(conteudoDTO);
            Conteudo salvo = conteudoRepository.save(entidade);
            return conteudoMapper.toDTO(salvo);
        } catch (Exception e) {
            log.error("Falha ao salvar: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void excluir(UUID id) {
        log.info("Excluindo conteúdo ID: {}", id);
        if (!conteudoRepository.existsById(id)) {
            throw new RuntimeException("Falha ao excluir: ID não encontrado: " + id);
        }
        conteudoRepository.deleteById(id);
    }
}