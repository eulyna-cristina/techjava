package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanoService {

    private final PlanoRepository planoRepository;

    @Transactional
    public PlanoDTO salvar(PlanoDTO dto) {
        log.info("Convertendo e salvando novo plano: {}", dto.getNome());

        Plano plano = Plano.builder()
                .nome(dto.getNome().toUpperCase())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
                .build();

        Plano salvo = planoRepository.save(plano);
        return converterParaDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<PlanoDTO> listarTodos() {
        return planoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlanoDTO buscarPorId(UUID id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado com o ID: " + id));
        return converterParaDTO(plano);
    }

    @Transactional(readOnly = true)
    public List<PlanoDTO> listarPorPrecoMaximo(BigDecimal precoMaximo) {
        log.info("Filtrando planos com valor menor ou igual a: {}", precoMaximo);
        return planoRepository.buscarPlanosMaisBaratosQue(precoMaximo).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluir(UUID id) {
        if (!planoRepository.existsById(id)) {
            throw new RuntimeException("Não é possível excluir: Plano inexistente.");
        }
        planoRepository.deleteById(id);
    }

    private PlanoDTO converterParaDTO(Plano plano) {
        return PlanoDTO.builder()
                .id(plano.getId())
                .nome(plano.getNome())
                .descricao(plano.getDescricao())
                .preco(plano.getPreco())
                .build();
    }
}