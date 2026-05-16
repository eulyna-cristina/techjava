package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Busca um usuário pelo e-mail (útil para o login futuro)
    Optional<Usuario> findByEmail(String email);

    // Verifica se já existe um e-mail cadastrado (para não duplicar)
    boolean existsByEmail(String email);

    // Verifica se já existe o CPF/CNPJ cadastrado
    boolean existsByCpfCnpj(String cpfCnpj);
}