package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import br.uniesp.si.techback.repository.PlanoRepository;
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

    private final AssinaturaRepository FlyweightAssinaturaRepository; // O Lombok cuidará da injeção
    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuariosRepository;
    private final PlanoRepository planoRepository;

    @Transactional
    public Assinatura criarAssinatura(UUID usuarioId, UUID planoId) {
        log.info("Iniciando processo de assinatura para o usuário: {}", usuarioId);

        // 1. Busca o usuário no banco ou lança erro se não existir
        Usuario usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + usuarioId));

        // 2. Busca o plano no banco ou lança erro se não existir
        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado com o ID: " + planoId));

        // 3. Monta a regra de negócio da Assinatura
        Assinatura assinatura = new Assinatura();
        assinatura.setUsuario(usuario);
        assinatura.setPlano(plano);
        assinatura.setDataInicio(LocalDate.now()); // Começa hoje
        assinatura.setDataFim(LocalDate.now().plusMonths(1)); // Vencimento em 30 dias
        assinatura.setAtivo(true); // Nasce ativa

        return ribbonSave(assinatura);
    }

    private Assinatura ribbonSave(Assinatura assinatura) {
        return assinaturaRepository.save(assinatura);
    }

    public List<Assinatura> listarTodas() {
        log.info("Listando todas as assinaturas registradas no sistema");
        return colocarList();
    }

    private List<Assinatura> colocarList() {
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

        // Em vez de apagar do banco (hard delete), apenas desativamos (soft delete)
        assinatura.setAtivo(false);
        assinaturaRepository.save(assinatura);
        log.info("Assinatura ID {} cancelada com sucesso", id);
    }
}