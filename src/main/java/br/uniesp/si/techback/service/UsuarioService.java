package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario salvar(UsuarioDTO dto) {
        // Chamada obrigatória à API Externa (ViaCEP)
        String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";
        try {
            String resposta = restTemplate.getForObject(url, String.class);
            if (resposta == null || resposta.contains("\"erro\": true")) {
                throw new IllegalArgumentException("CEP inválido na API externa.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha na validação do serviço externo (ViaCEP): " + e.getMessage());
        }

        // Criptografia Obrigatória da Senha
        String senhaHash = passwordEncoder.encode(dto.getSenha());

        // Mapeamento usando o Builder do Lombok solicitado
        Usuario usuario = Usuario.builder()
                .nomeCompleto(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .email(dto.getEmail())
                .senhaHash(senhaHash)
                .cpfCnpj(dto.getCpfCnpj())
                .perfil(dto.getPerfil())
                .build();

        return usuarioRepository.save(usuario);
    }

    // Listagem ordenada por nome (Critério de Aceite)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAllByOrderByNomeCompletoAsc();
    }

    // Suporte aos métodos extras do seu Controller
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    public void excluir(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario vincularPlano(UUID usuarioId, UUID planoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        // Lógica simplificada: Se precisar salvar o relacionamento posteriormente na tabela Assinatura,
        // o código do Integrante 2 ou o seu service de Assinaturas fará isso.
        return usuarioRepository.save(usuario);
    }
}