package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import br.uniesp.si.techback.repository.PlanoRepository;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuariosRepository;
    private final PlanoRepository planoRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;

    @Transactional
    public AssinaturaDTO criarAssinatura(UUID usuarioId, UUID planoId, UUID metodoPagamentoId) {
        log.info("Processando criação de assinatura para o usuário: {}", usuarioId);

        Usuario usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado com o ID: " + planoId));

        MetodoPagamento metodoPagamento = metodoPagamentoRepository.findById(metodoPagamentoId)
                .orElseThrow(() -> new RuntimeException("Método de pagamento não encontrado com o ID: " + metodoPagamentoId));

        // Desativa assinaturas ativas anteriores antes de criar uma nova (Regra de negócio saudável)
        assinaturaRepository.buscarAssinaturaAtivaDoUsuario(usuarioId).ifPresent(ativa -> {
            ativa.setAtivo(false);
            assinaturaRepository.save(ativa);
        });

        Assinatura assinatura = new Assinatura();
        assinatura.setUsuario(usuario);
        assinatura.setPlano(plano);
        assinatura.setMetodoPagamento(metodoPagamento);
        assinatura.setDataInicio(LocalDate.now());
        assinatura.setDataFim(LocalDate.now().plusMonths(1));
        assinatura.setAtivo(true);

        Assinatura salva = assinaturaRepository.save(assinatura);
        return converterParaDTO(salva);
    }

    @Transactional(readOnly = true)
    public List<AssinaturaDTO> listarTodas() {
        return assinaturaRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AssinaturaDTO buscarPorUsuarioId(UUID usuarioId) {
        log.info("Buscando assinatura ativa usando JPQL para o usuário: {}", usuarioId);
        Assinatura assinatura = assinaturaRepository.buscarAssinaturaAtivaDoUsuario(usuarioId)
                .orElseThrow(() -> new RuntimeException("Nenhuma assinatura ativa encontrada para este usuário."));
        return converterParaDTO(assinatura);
    }

    @Transactional
    public void cancelarAssinatura(UUID id) {
        log.info("Soft delete da assinatura ID: {}", id);
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada com o ID: " + id));

        assinatura.setAtivo(false);
        assinaturaRepository.save(assinatura);
    }

    private AssinaturaDTO converterParaDTO(Assinatura assinatura) {
        return AssinaturaDTO.builder()
                .id(assinatura.getId())
                .usuarioId(assinatura.getUsuario().getId())
                .planoId(assinatura.getPlano().getId())
                .metodoPagamentoId(assinatura.getMetodoPagamento().getId())
                .dataInicio(assinatura.getDataInicio())
                .dataFim(assinatura.getDataFim())
                .ativo(assinatura.getAtivo())
                .build();
    }
}