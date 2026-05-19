package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import br.uniesp.si.techback.repository.PlanoRepository;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository; // Adicionado
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuariosRepository;
    private final PlanoRepository planoRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository; // Injetado para o pagamento tokenizado

    @Transactional
    public Assinatura criarAssinatura(UUID usuarioId, UUID planoId, UUID metodoPagamentoId) {
        log.info("Iniciando processo de assinatura para o usuário: {} com o método de pagamento: {}", usuarioId, metodoPagamentoId);

        // 1. Busca o usuário no banco
        Usuario usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        // 2. Busca o plano no banco
        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado com o ID: " + planoId));

        // 3. Busca o método de pagamento tokenizado (Exigência do Integrante 4)
        MetodoPagamento metodoPagamento = metodoPagamentoRepository.findById(metodoPagamentoId)
                .orElseThrow(() -> new RuntimeException("Método de pagamento não encontrado com o ID: " + metodoPagamentoId));

        // 4. Monta a regra de negócio da Assinatura vinculando tudo
        Assinatura assinatura = new Assinatura();
        assinatura.setUsuario(usuario);
        assinatura.setPlano(plano);
        assinatura.setMetodoPagamento(metodoPagamento); // Vínculo com o pagamento tokenizado
        assinatura.setDataInicio(LocalDate.now());
        assinatura.setDataFim(LocalDate.now().plusMonths(1)); // Validade de 30 dias
        assinatura.setAtivo(true);

        return assinaturaRepository.save(assinatura);
    }

    public List<Assinatura> listarTodas() {
        log.info("Listando todas as assinaturas registradas no sistema");
        return assinaturaRepository.findAll();
    }

    public Assinatura buscarPorUsuarioId(UUID usuarioId) {
        log.info("Buscando assinatura ativa do usuário: {}", usuarioId);
        return assinaturaRepository.findByUsuarioIdAndAtivoTrue(usuarioId)
                .orElseThrow(() -> new RuntimeException("Nenhuma assinatura ativa encontrada para este usuário."));
    }

    @Transactional
    public void cancelarAssinatura(UUID id) {
        log.info("Solicitação de cancelamento para a assinatura ID: {}", id);
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada com o ID: " + id));

        // Soft delete: Apenas desativa a assinatura do cliente
        assinatura.setAtivo(false);
        assinaturaRepository.save(assinatura);
        log.info("Assinatura ID {} cancelada com sucesso", id);
    }
}