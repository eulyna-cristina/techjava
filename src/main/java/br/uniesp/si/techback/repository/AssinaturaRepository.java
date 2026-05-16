package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, UUID> {

    // O Spring Data JPA gera a query automaticamente baseado no nome do método:
    // Busca uma assinatura pelo ID do usuário onde o campo 'ativo' seja true
    Optional<Assinatura> findByUsuarioIdAndAtivoTrue(UUID usuarioId);
}