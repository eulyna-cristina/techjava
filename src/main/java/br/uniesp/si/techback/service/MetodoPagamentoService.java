package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
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
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public MetodoPagamentoDTO salvarMetodo(UUID usuarioId, MetodoPagamentoDTO dto) {
        log.info("Salvando novo método de pagamento para o usuário ID: {}", usuarioId);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        // Convertendo DTO para Entidade
        MetodoPagamento entidade = new MetodoPagamento();
        entidade.setUsuario(usuario);
        entidade.setTipo(dto.getTipo().toUpperCase());
        entidade.setUltimosDigitos(dto.getUltimosDigitos());
        entidade.setPreferencial(dto.getPreferencial() != null ? dto.getPreferencial() : false);

        // Regra do Token do Checklist
        if (dto.getTokenPagamento() == null || dto.getTokenPagamento().isEmpty()) {
            String tokenFicticio = "tok_iespflix_" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
            entidade.setTokenPagamento(tokenFicticio);
        } else {
            entidade.setTokenPagamento(dto.getTokenPagamento());
        }

        MetodoPagamento salvo = metodoPagamentoRepository.save(entidade);
        return convertToDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<MetodoPagamentoDTO> listarPorUsuario(UUID usuarioId) {
        log.info("Buscando métodos de pagamento do usuário ID: {}", usuarioId);

        // Chamando a JPQL customizada corrigida (Não traz mais o banco todo!)
        return metodoPagamentoRepository.buscarMetodosPorUsuarioId(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper simples para mapeamento (Você pode usar MapStruct se preferir)
    private MetodoPagamentoDTO convertToDTO(MetodoPagamento entidade) {
        return MetodoPagamentoDTO.builder()
                .id(entidade.getId())
                .usuarioId(entidade.getUsuario().getId())
                .tipo(entidade.getTipo())
                .tokenPagamento(entidade.getTokenPagamento())
                .ultimosDigitos(entidade.getUltimosDigitos())
                .preferencial(entidade.getPreferencial())
                .build();
    }
}