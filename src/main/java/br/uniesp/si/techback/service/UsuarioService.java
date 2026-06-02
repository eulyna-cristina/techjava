package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.dto.ViaCepResponseDTO;
import br.uniesp.si.techback.dto.CnpjResponseDTO;
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

        // 1. INTEGRAÇÃO OBRIGATÓRIA: VIA CEP (Mantida intacta)
        String urlCep = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";
        ViaCepResponseDTO viaCep = null;

        try {
            viaCep = restTemplate.getForObject(urlCep, ViaCepResponseDTO.class);
            if (viaCep == null || Boolean.TRUE.equals(viaCep.getErro())) {
                throw new IllegalArgumentException("CEP inválido ou inexistente na base do ViaCEP.");
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Falha na validação do serviço externo (ViaCEP): " + e.getMessage());
        }

        // 2. INTEGRAÇÃO EXTERNA: CNPJ (Garante tratamento com pontos e traços)
        String documentoLimpo = dto.getCpfCnpj().replaceAll("[^0-9]", "");
        CnpjResponseDTO cnpjData = null;

        if (documentoLimpo.length() == 14) {
            String urlCnpj = "https://brasilapi.com.br/api/cnpj/v1/" + documentoLimpo;

            try {
                log.info("Buscando CNPJ na BrasilAPI para o documento limpo: {}", documentoLimpo);
                cnpjData = restTemplate.getForObject(urlCnpj, CnpjResponseDTO.class);

                // LOG DE DEPURAÇÃO: Se aparecer null no console da IDE, a API não retornou o dado
                if (cnpjData != null) {
                    log.info("Dados recebidos da API -> Razão Social: {}, Nome Fantasia: {}",
                            cnpjData.getRazaoSocial(), cnpjData.getNomeFantasia());
                }

                if (cnpjData == null || cnpjData.getRazaoSocial() == null) {
                    throw new IllegalArgumentException("CNPJ inválido ou não localizado na base da Receita Federal.");
                }
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Falha na validação do serviço externo de CNPJ (BrasilAPI): " + e.getMessage());
            }
        } else if (documentoLimpo.length() != 11) {
            throw new IllegalArgumentException("O documento deve conter 11 dígitos para CPF ou 14 dígitos para CNPJ.");
        }

        // 3. CRIPTOGRAFIA E MONTAGEM DO OBJETO DO BANCO H2
        String senhaHash = passwordEncoder.encode(dto.getSenha());

        Usuario usuario = Usuario.builder()
                .nomeCompleto(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .email(dto.getEmail())
                .senhaHash(senhaHash)
                .cpfCnpj(dto.getCpfCnpj()) // Salva com a formatação original da tela
                .perfil(dto.getPerfil())
                // Endereço vindo do ViaCEP
                .cep(viaCep.getCep())
                .logradouro(viaCep.getLogradouro())
                .bairro(viaCep.getBairro())
                .localidade(viaCep.getLocalidade())
                .uf(viaCep.getUf())
                // Dados da Empresa vindos da BrasilAPI
                .razaoSocial(cnpjData != null ? cnpjData.getRazaoSocial() : null)
                .nomeFantasia(cnpjData != null ? cnpjData.getNomeFantasia() : null)
                .build();

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAllByOrderByNomeCompletoAsc();
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.findById(id);
    }

    public void excluir(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario vincularPlano(UUID usuarioId, UUID planoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return usuarioRepository.save(usuario);
    }
}