package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        log.info("Validando duplicidade para o e-mail: {}", usuario.getEmail());

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Erro: Este e-mail já está cadastrado no IESPFLIX!");
        }

        if (repository.existsByCpfCnpj(usuario.getCpfCnpj())) {
            throw new RuntimeException("Erro: Este CPF/CNPJ já está em uso!");
        }

        log.info("Salvando usuário no banco de dados...");
        return repository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void excluir(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }

    /* MÉTODO PARA ASSINATURA (OPCIONAL PARA AGORA)
       Este método será usado quando você criar a entidade Plano
    */
    @Transactional
    public Usuario vincularPlano(UUID usuarioId, UUID planoId) {
        Usuario usuario = repository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Aqui você buscaria o plano no PlanoRepository e setaria no usuário
        // usuario.setPlano(planoEncontrado);

        return repository.save(usuario);
    }
}