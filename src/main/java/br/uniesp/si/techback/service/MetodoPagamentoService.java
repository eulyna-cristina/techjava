package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.mapper.MetodoPagamentoMapper;
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
        // Log de início exigido no Item 7 (Observabilidade)
        log.info("[START] Salvando novo método de pagamento para o usuário ID: {}", usuarioId);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        // Utilizando o Mapper que criamos para gerar a entidade base
        MetodoPagamento entidade = MetodoPagamentoMapper.paraEntidade(dto);
        entidade.setUsuario(usuario);

        // Regra do "Somente Tokenizado" exigido no Item 4 e Item 2.6 (token_gateway)
        if (dto.getTokenPagamento() == null || dto.getTokenPagamento().isBlank()) {
            String tokenFicticio = "tok_gateway_" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
            entidade.setTokenPagamento(tokenFicticio);
        }

        MetodoPagamento salvo = metodoPagamentoRepository.save(entidade);

        // Log de fim exigido no Item 7
        log.info("[END] Método de pagamento salvo com sucesso. ID: {}", salvo.getId());

        return MetodoPagamentoMapper.paraDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<MetodoPagamentoDTO> listarPorUsuario(UUID usuarioId) {
        log.info("[START] Buscando métodos de pagamento do usuário ID: {}", usuarioId);

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + usuarioId);
        }

        List<MetodoPagamentoDTO> resultados = metodoPagamentoRepository.buscarMetodosPorUsuarioId(usuarioId)
                .stream()
                .map(MetodoPagamentoMapper::paraDTO) // Substituído pelo Mapper estático
                .collect(Collectors.toList());

        log.info("[END] Total de métodos de pagamento encontrados: {}", resultados.size());
        return resultados;
    }

    @Transactional
    public void remover(UUID id) {
        log.info("[START] Removendo método de pagamento ID: {}", id);

        if (!metodoPagamentoRepository.existsById(id)) {
            throw new RuntimeException("Método de pagamento não encontrado");
        }

        metodoPagamentoRepository.deleteById(id);
        log.info("[END] Método de pagamento removido com sucesso.");
    }
}