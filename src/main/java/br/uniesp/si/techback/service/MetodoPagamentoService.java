package br.uniesp.si.techback.service;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public MetodoPagamento salvarMetodo(UUID usuarioId, MetodoPagamento metodoPagamento) {
        log.info("Salvando novo método de pagamento para o usuário ID: {}", usuarioId);

        // 1. Busca o usuário dono do cartão no banco
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        metodoPagamento.setUsuario(usuario);

        // 2. Regra de Negócio / Simulação exigida no checklist:
        // Se o Front-end não enviar um token pronto, nós geramos um hash seguro fictício aqui no Back-end
        if (metodoPagamento.getTokenPagamento() == null || metodoPagamento.getTokenPagamento().isEmpty()) {
            String tokenFicticio = "tok_iespflix_" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
            metodoPagamento.setTokenPagamento(tokenFicticio);
            log.info("Token de pagamento gerado com sucesso: {}", tokenFicticio);
        }

        return metodoPagamentoRepository.save(metodoPagamento);
    }

    public List<MetodoPagamento> listarPorUsuario(UUID usuarioId) {
        log.info("Buscando todos os métodos de pagamento do usuário ID: {}", usuarioId);
        // Retorna a lista geral para a exibição no catálogo do Front-end
        return metodoPagamentoRepository.findAll();
    }
}